/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ClienteDTO;
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
 *Clase que implementa el recurso "Clientes"
 * @author Elias David Negrete Salgado
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource {
    
    
    @GET
    @Path("{clienteId: \\d+}")
    public ClienteDTO darCliente(@PathParam("pagosId") Long clienteId){
        return new ClienteDTO();
    }
    
    
    @POST
    public ClienteDTO crearCliente(ClienteDTO pCliente){
        return pCliente;
    }
    
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDTO modificarCliente(@PathParam("clienteId") Long clienteId, ClienteDTO pCliente){
        return pCliente;
    }
    
    
    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) {
        
    }
    
}
