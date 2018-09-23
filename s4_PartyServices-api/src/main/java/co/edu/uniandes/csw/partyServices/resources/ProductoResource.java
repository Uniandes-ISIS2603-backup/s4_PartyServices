/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProductoDTO;
import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
    
     private static final Logger LOGGER = Logger.getLogger(ProductoResource.class.getName());

    @Inject
    private ProductoLogic productoLogic;

    
    
     @GET
     @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO consultarProducto(@PathParam("producto")String producto)
    {
        
        LOGGER.info("Obteniendo producto por nombre");
        ProductoDTO ret =  new ProductoDTO(  productoLogic.findByNombre(producto));
        LOGGER.log(Level.INFO, "Saliendo de obtener el producto por nombre");
        return ret;
        
       
    }
    @POST
    public ProductoDTO crearProducto(ProductoDTO pProducto) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ProductoResource createProducto: input: {0}", pProducto.toString());
        ProductoDTO productoDTO = new ProductoDTO(productoLogic.createProducto(pProducto.toEntity()));
        LOGGER.log(Level.INFO, "ProductoResource createProducto: output: {0}", productoDTO.toString());
        return productoDTO;
                
    }
    
      @DELETE
      @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public void borrarProducto(@PathParam("producto")String producto) throws BusinessLogicException
    {
        
         LOGGER.log(Level.INFO, "ProductoResource deleteProducto: input: {0}", producto);
        ProductoEntity entity = productoLogic.findByNombre(producto);
        
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /productos/" + producto + " no existe.", 404);
        }
     
        productoLogic.deleteProducto(producto);
       
        LOGGER.info("ProductoResource deleteProducto: output: void");
    }
    
    @PUT
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO actualizarProducto(@PathParam("producto")String producto, ProductoDTO pProducto) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "ProductoResource updateProducto: input: id: {0} , book: {1}");
        pProducto.setNombre(producto);
        ProductoEntity entity = productoLogic.findByNombre(producto);
        if (entity== null) 
        {
            throw new WebApplicationException("El recurso /productos/" + producto + " no existe.", 404);
        }
        
        ProductoEntity updateado = productoLogic.updateProducto(producto,pProducto.toEntity());
        
        ProductoDTO detailDTO = new ProductoDTO(updateado);
       
        LOGGER.log(Level.INFO, "ProductoResource updateProducto: output: {0}", detailDTO.toString());
        return detailDTO;
       
    }
    
}
