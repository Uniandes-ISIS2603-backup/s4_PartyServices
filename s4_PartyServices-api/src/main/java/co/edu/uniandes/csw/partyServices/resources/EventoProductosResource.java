/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;
import co.edu.uniandes.csw.partyServices.dtos.ProductoDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.EventoProductosLogic;
import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tomas Vargas Leal
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoProductosResource {
    
   private static final Logger LOGGER = Logger.getLogger(EventoProductosResource.class.getName());

    @Inject
    private EventoProductosLogic eventoProductoLogic;

    @Inject
    private ProductoLogic productoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un producto existente con un autor existente
     *
     * @param nombreEvento El nombre del evento al cual se le va a asociar el producto
     * @param nombreProducto El ID del producto que se asocia
     * @return JSON {@link ProductoDetailDTO} - El producto asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el producto.
     */
    @POST
    @Path("{nombreProducto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDetailDTO addProducto(@PathParam("nombreEvento") String nombreEvento, @PathParam("nombreProducto") String nombreProducto) {
        
        
        LOGGER.log(Level.INFO, "EventoProductosResource addProducto: input: eventosId {0} , productosId {1}", new Object[]{nombreEvento, nombreProducto});
        if (productoLogic.getProducto(nombreProducto) == null) {
            throw new WebApplicationException("El recurso /productos/" + nombreProducto + " no existe.", 404);
        }
        
        ProductoDetailDTO detailDTO = new ProductoDetailDTO(eventoProductoLogic.addProducto(nombreEvento, nombreProducto));
        LOGGER.log(Level.INFO, "EventoProductosResource addProducto: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los productos que existen en un evento.
     *
     * @param nombreEvento El nombre del evento del cual se buscan los productos
     * @return JSONArray {@link ProductoDetailDTO} - Los productos encontrados en el
     * evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProductoDetailDTO> getProductos(@PathParam("nombreEvento") String nombreEvento) 
    {
        LOGGER.log(Level.INFO, "EventoProductosResource getProductos: input: {0}", nombreEvento);
        List<ProductoDetailDTO> lista = productosListEntity2DTO(eventoProductoLogic.getProductos(nombreEvento));
        LOGGER.log(Level.INFO, "EventoProductosResource getProductos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el producto con el ID recibido en la URL, relativo a un
     * autor.
     *
     * @param nombreEvento El ID del autor del cual se busca el producto
     * @param nombreProducto El ID del producto que se busca
     * @return {@link ProductoDetailDTO} - El producto encontrado en el autor.
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el producto.
     */
    @GET
    @Path("{nombreProducto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDetailDTO getProducto(@PathParam("nombreEvento") String nombreEvento, @PathParam("nombreProducto") String nombreProducto) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "EventoProductosResource getProducto: input: eventosId {0} , productosId {1}", new Object[]{nombreEvento, nombreProducto});
        if (productoLogic.getProducto(nombreProducto) == null) {
            throw new WebApplicationException("El recurso /productos/" + nombreProducto + " no existe.", 404);
        }
        ProductoDetailDTO detailDTO = new ProductoDetailDTO(eventoProductoLogic.getProducto(nombreEvento, nombreProducto));
        LOGGER.log(Level.INFO, "EventoProductosResource getProducto: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de productos de un autor con la lista que se recibe en el
     * cuerpo
     *
     * @param nombreEvento El ID del autor al cual se le va a asociar el producto
     * @param productos JSONArray {@link ProductoDetailDTO} - La lista de productos que se
     * desea guardar.
     * @return JSONArray {@link ProductoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el producto.
     */
    @PUT
    public List<ProductoDetailDTO> replaceProductos(@PathParam("nombreEvento") String nombreEvento, List<ProductoDetailDTO> productos) {
        LOGGER.log(Level.INFO, "EventoProductosResource replaceProductos: input: eventosId {0} , productos {1}", new Object[]{nombreEvento, productos});
       
        for(ProductoDetailDTO producto : productos) {
            if (productoLogic.getProductoId(producto.getId()) == null) {
                throw new WebApplicationException("El recurso /productos/" + producto.getId() + " no existe.", 404);
            }
        }
        List<ProductoDetailDTO> lista = productosListEntity2DTO(eventoProductoLogic.replaceProductos(nombreEvento, productosListDTO2Entity(productos)));
        LOGGER.log(Level.INFO, "EventoProductosResource replaceProductos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el producto y e autor recibidos en la URL.
     *
     * @param nombreEvento El ID del autor al cual se le va a desasociar el producto
     * @param nombreProducto El ID del producto que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el producto.
     */
    @DELETE
    @Path("{nombreProducto: [a-zA-Z][a-zA-Z]*}")
    public void removeProducto(@PathParam("nombreEvento") String nombreEvento, @PathParam("nombreProducto") String nombreProducto) {
        LOGGER.log(Level.INFO, "EventoProductosResource deleteProducto: input: nombreEvento {0} , productosId {1}", new Object[]{nombreEvento, nombreProducto});
        if (productoLogic.getProducto(nombreProducto) == null) {
            throw new WebApplicationException("El recurso /productos/" + nombreProducto + " no existe.", 404);
        }
        if (productoLogic == null) {
            throw new WebApplicationException("El recurso /productos/" + nombreProducto + " no existe.", 404);
        }
        eventoProductoLogic.removeProducto(nombreEvento, nombreProducto);
        
        LOGGER.info("EventoProductosResource deleteProducto: output: void");
    }

    /**
     * Convierte una lista de ProductoEntity a una lista de ProductoDetailDTO.
     *
     * @param entityList Lista de ProductoEntity a convertir.
     * @return Lista de ProductoDetailDTO convertida.
     */
    private List<ProductoDetailDTO> productosListEntity2DTO(List<ProductoEntity> entityList) {
        List<ProductoDetailDTO> list = new ArrayList<>();
        for (ProductoEntity entity : entityList) {
            list.add(new ProductoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ProductoDetailDTO a una lista de ProductoEntity.
     *
     * @param dtos Lista de ProductoDetailDTO a convertir.
     * @return Lista de ProductoEntity convertida.
     */
    private List<ProductoEntity> productosListDTO2Entity(List<ProductoDetailDTO> dtos) {
        List<ProductoEntity> list = new ArrayList<>();
        for (ProductoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
