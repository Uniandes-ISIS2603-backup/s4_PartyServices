/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProductoDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
@Path ("producto")
@Produces ("application/json")
@Consumes ("application/json")
@RequestScoped
/**
 *
 * @author estudiante
 */
public class ProductoResource 
{
    
     @GET
     @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO consultarProducto(@PathParam("producto")String producto)
    {
        return new ProductoDTO();
    }
    @POST
    public ProductoDTO crearProducto(ProductoDTO pProducto)
    {
        return pProducto ;
    }
    
      @DELETE
      @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public void borrarProducto(@PathParam("producto")String producto)
    {
        
    }
    
    @PUT
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO actualizarProducto(@PathParam("producto")String producto, ProductoDTO pProducto)
    {
        return pProducto ;
    }
    
}
