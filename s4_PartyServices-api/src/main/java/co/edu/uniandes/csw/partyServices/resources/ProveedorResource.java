/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProveedorDTO;
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
 *
 * @author estudiante
 */
@Path("proveedor")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
    public class ProveedorResource 
{
    
     @GET
    public ProveedorDTO getProveedor(){
        return new ProveedorDTO();
    }
        @POST
    public ProveedorDTO crearProveedor(ProveedorDTO pProveedor)
    {
        return pProveedor ;
    }
    
      @DELETE
      @Path("{proveedor: [a-zA-Z][a-zA-Z]*}")
    public void borrarProveedor(@PathParam("proveedor")String proveedor)
    {
        
    }
    
    @PUT
    @Path("{proveedor: [a-zA-Z][a-zA-Z]*}")
    public ProveedorDTO actualizarProveedor(@PathParam("proveedor")String proveedor, ProveedorDTO pProveedor)
    {
        return pProveedor ;
    }
    


}