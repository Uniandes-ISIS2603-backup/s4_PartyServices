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
import java.util.ArrayList;
import java.util.List;
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

@Path("producto")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
/**
 *
 * @author Andres
 */
public class ProductoResource {

    /**
     * Log para el registro de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(ProductoResource.class.getName());

    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de
     * dependencias.
     */
    @Inject
    private ProductoLogic productoLogic;

    /**
     * Busca y devuelve todos los eventos que existen en la aplicacion.
     * @return JSONArray {@link ProductoDTO} - Los productos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProductoDTO> getProductos() {
        List<ProductoDTO> listaProductos = new ArrayList<>();
        LOGGER.info("ProductoResource getProductos: input: void");
        for (ProductoEntity produc : productoLogic.findAll()) {
            listaProductos.add(new ProductoDTO(produc));
        }
        LOGGER.log(Level.INFO, "ProductoResource getBooks: output: {0}", listaProductos);
        return listaProductos;
    }

    /**
     * Busca el producto con el nombre recibido en la URL y lo retorna
     * @param producto nombre del producto a consultar
     * @return el producto buscado
     */
    @GET
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO consultarProducto(@PathParam("producto") String producto) {

        LOGGER.info("Obteniendo producto por nombre");
        
        ProductoEntity entity = productoLogic.findByNombre(producto) ;
        
        if (entity == null) {
            throw new WebApplicationException("El recurso /producto/" + producto + " no existe.", 404);
        }
        
        ProductoDTO ret = new ProductoDTO(entity);   
        
        LOGGER.log(Level.INFO, "Saliendo de obtener el producto por nombre");
        return ret;

    }

    /**
     * Crea un nuevo producto con la informacion que se recibe en el cuerpo de la
     * peticion y se regresa un objeto identico con un id auto-generado por la
     * base de datos
     * @param pProducto El producto que se desea guardar
     * @return el producto guardado
     * @throws BusinessLogicException Si alguna de las reglas de nogocio no se cumple
     */
    @POST
    public ProductoDTO crearProducto(ProductoDTO pProducto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProductoResource createProducto: input: {0}", pProducto);
        ProductoDTO productoDTO = new ProductoDTO(productoLogic.createProducto(pProducto.toEntity()));
        LOGGER.log(Level.INFO, "ProductoResource createProducto: output: {0}", productoDTO);
        return productoDTO;

    }

    /**
     * Elmina el producto con el nombre recibido en la URL
     * @param producto nombre del producto a borrar
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @DELETE
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public void borrarProducto(@PathParam("producto") String producto) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "ProductoResource deleteProducto: input: {0}", producto);
        ProductoEntity entity = productoLogic.findByNombre(producto);

        if (entity == null) {
            throw new WebApplicationException("El recurso /productos/" + producto + " no existe.", 404);
        }

        productoLogic.deleteProducto(producto);

        LOGGER.info("ProductoResource deleteProducto: output: void");
    }

    /**
     * Actualiza el producto con el nombre recibido en la URL con los valores
     * recibidos en el cuerpo de la petición
     * @param pNombre nombre del producto que se busca actualizar
     * @param producto Producto con la informacion que se desea actualizar 
     * @return el producto actualizado 
     * @throws BusinessLogicException Si alguna de las reglas de negocio no se cumple
     */
    @PUT
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO actualizarProducto(@PathParam("producto") String pNombre, ProductoDTO producto) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "ProductoResource updateProducto: input: id: {0} , book: {1}");
        producto.setNombre(pNombre);
        
        ProductoEntity entity = productoLogic.findByNombre(pNombre);
        
        if (entity == null) {
            throw new WebApplicationException("El recurso /productos/" + producto + " no existe.", 404);
        }

        producto.setId(entity.getId());
        
        ProductoEntity updateado = productoLogic.updateProducto(pNombre, producto.toEntity());

        ProductoDTO detailDTO = new ProductoDTO(updateado);

        LOGGER.log(Level.INFO, "ProductoResource updateProducto: output: {0}", detailDTO);
        
        return detailDTO;

    }

}
