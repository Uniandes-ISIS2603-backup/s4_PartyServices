/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.PagoDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *Clase que implementa el recurso "Pagos"
 * @author Elias David Negrete Salgado
 */
@Path("pagos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {
    
    
    @Path("{pagosId: \\d+}")
    public PagoDTO darPago(@PathParam("pagosId") Long pagosId){
        return new PagoDTO();
    }
    
    
    @POST
    @Path("{pagosId: \\d+}")
    public PagoDTO crearPago(PagoDTO PAGO){
        return PAGO;
    }
    
    
    @PUT
    @Path("{pagosId: \\d+}")
    public PagoDTO modificarPago(@PathParam("pagosId") Long pagosId, PagoDTO pago){
        return pago;
    }
    
    
    @DELETE
    @Path("{pagosId: \\d+}")
    public void deletePago(@PathParam("pagosId") Long pagosId) {
        
    }
    
}
