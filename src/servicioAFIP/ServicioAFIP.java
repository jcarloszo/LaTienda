/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioAFIP;
import fev1.dif.afip.gov.ar.AlicIva;
import fev1.dif.afip.gov.ar.ArrayOfAlicIva;
import fev1.dif.afip.gov.ar.ArrayOfFECAEDetRequest;
import fev1.dif.afip.gov.ar.FEAuthRequest;
import fev1.dif.afip.gov.ar.FECAECabRequest;
import fev1.dif.afip.gov.ar.FECAEDetRequest;
import fev1.dif.afip.gov.ar.FECAERequest;
import fev1.dif.afip.gov.ar.FECAEResponse;
import fev1.dif.afip.gov.ar.FERecuperaLastCbteResponse;
import modelo.Comprobante;
import modelo.EnumTipoComprobante;
import org.datacontract.schemas._2004._07.sge_service_contracts.Autorizacion;
import servicioautorizacion.ServicioAutorizacion;
import servicioafip.SolicitudAFIP;


/**
 *
 * @author ZURITA
 */
public class ServicioAFIP {
    private static Autorizacion autorizacion;
    
    private static void solicitarAutorizacion(){
        autorizacion = ServicioAutorizacion.solicitarAutorizacion("5947C49C-B34E-4845-A6B8-C3E3637A5332");
    }
    
    public static boolean autorizarComprobante(Comprobante comprobante){
        ///////
        solicitarAutorizacion();
        FEAuthRequest feAuthRequest = new FEAuthRequest();
        feAuthRequest.setCuit(autorizacion.getCuit());
        feAuthRequest.setSign(autorizacion.getSign().getValue());
        feAuthRequest.setToken(autorizacion.getToken().getValue());
        ///////
        
        int tipoComprobante;
        if(comprobante.getTipoComprobante().equals(EnumTipoComprobante.FacturaA)) tipoComprobante = 1;
        else tipoComprobante = 6;
         
        int ptoDeVenta = comprobante.getPuntoDeVenta().getNumero();
        
        //Obtengo el ultimo comprobante
        FERecuperaLastCbteResponse ultimoComprobante = SolicitudAFIP.feCompUltimoAutorizado(feAuthRequest, ptoDeVenta, tipoComprobante);
        int ultimoCbteNumero = ultimoComprobante.getCbteNro();
        
        //CREACION DEL REQUEST PARA FECAESolicitar
        FECAERequest fecaeRequest = new FECAERequest();
        
        FECAECabRequest feCabReq = new FECAECabRequest();
        feCabReq.setPtoVta(ptoDeVenta);
        feCabReq.setCbteTipo(tipoComprobante);
        feCabReq.setCantReg(1);
        fecaeRequest.setFeCabReq(feCabReq);
        
        FECAEDetRequest feDetReq = new FECAEDetRequest();
        feDetReq.setConcepto(1);
        feDetReq.setDocTipo(80);
        feDetReq.setDocNro(comprobante.getVenta().getCliente().getCuit());
        feDetReq.setCbteDesde(ultimoCbteNumero+1);
        feDetReq.setCbteHasta(ultimoCbteNumero+1);
        feDetReq.setCbteFch(comprobante.getFecha());
        feDetReq.setImpTotal(dosDecimales(comprobante.getVenta().getTotal()));
        feDetReq.setImpTotConc(0);
        feDetReq.setImpNeto(comprobante.getVenta().getTotalNeto());
        feDetReq.setImpIVA(comprobante.getVenta().getTotalIva());
        feDetReq.setImpTrib(0);
        feDetReq.setMonId("PES");
        feDetReq.setMonCotiz(1);
        
        ArrayOfAlicIva arrayOfAlicIva = new ArrayOfAlicIva();
        AlicIva alicuota = new AlicIva();
        alicuota.setId(5);
        alicuota.setBaseImp(comprobante.getVenta().getTotalNeto());
        alicuota.setImporte(comprobante.getVenta().getTotalIva());
        arrayOfAlicIva.getAlicIva().add(alicuota);
   
        feDetReq.setIva(arrayOfAlicIva);
        
        ArrayOfFECAEDetRequest arrayOfFeDetReq = new ArrayOfFECAEDetRequest();
        arrayOfFeDetReq.getFECAEDetRequest().add(feDetReq);
        fecaeRequest.setFeDetReq(arrayOfFeDetReq);
        
        //Realizo la solicitud
        FECAEResponse fecaeResponse = SolicitudAFIP.fecaeSolicitar(feAuthRequest, fecaeRequest);
        
        try {
            System.out.println("\n"+fecaeResponse.getFeDetResp().getFECAEDetResponse().get(0).getObservaciones().getObs().get(0).getMsg());
        } catch(Exception e){}
        
        boolean autorizado = fecaeResponse.getFeCabResp().getResultado().equals("A");
        
        if(autorizado){
            System.out.println("Resultado de la solicitud: "+fecaeResponse.getFeCabResp().getResultado());
            System.out.println("Tipos Comprobante: "+fecaeResponse.getFeCabResp().getCbteTipo());
            System.out.println("Punto de Venta: "+fecaeResponse.getFeCabResp().getPtoVta());
            System.out.println("CUIT: "+fecaeResponse.getFeCabResp().getCuit());
            System.out.println("CAE: "+fecaeResponse.getFeDetResp().getFECAEDetResponse().get(0).getCAE());
            System.out.println("VencimientoCAE: "+fecaeResponse.getFeDetResp().getFECAEDetResponse().get(0).getCAEFchVto());
            System.out.println("NroCbte: "+fecaeResponse.getFeDetResp().getFECAEDetResponse().get(0).getCbteDesde());
            System.out.println("CUIT: "+fecaeResponse.getFeDetResp().getFECAEDetResponse().get(0).getDocNro());
        }
        
        return autorizado;
        
    }
    
    private static double dosDecimales(double value){
        return Math.round(value*100.0)/100.0;
    }
}
