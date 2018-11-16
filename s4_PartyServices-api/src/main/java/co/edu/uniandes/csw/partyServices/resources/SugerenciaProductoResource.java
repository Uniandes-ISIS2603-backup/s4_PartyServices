
package co.edu.uniandes.csw.partyServices.resources;


import co.edu.uniandes.csw.partyServices.dtos.ProductoDTO;
import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
import co.edu.uniandes.csw.partyServices.ejb.SugerenciaProductoLogic;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SugerenciaProductoResource {

    private static final Logger LOGGER = Logger.getLogger(SugerenciaProductoResource.class.getName());

    @Inject
    private SugerenciaProductoLogic sugerenciaProductoLogic;

    @Inject
    private ProductoLogic productoLogic; 
    
    //Clase que implementa el recurso "cliente/{id}tematicas/{id}/sugerencias/{id}".
    //@Path("{tematicasId: \\d+}/sugerencias/{sugerenciasId: \\d+}")
    @POST
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO addProducto(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId, @PathParam("producto") String productoId) {
        LOGGER.log(Level.INFO, "SugerenciaProductoResource addProducto: input: sugerenciaId {0} , productoId {1}", new Object[]{sugerenciasId, productoId});
        if (productoLogic.getProducto(productoId) == null) {
            throw new WebApplicationException("El recurso /productos/" + productoId + " no existe.", 404);
        }
       ProductoEntity nuevo = productoLogic.getProducto(productoId);
        ProductoDTO productoDTO = new ProductoDTO(sugerenciaProductoLogic.addProducto(tematicasId, sugerenciasId, nuevo.getId()));
        LOGGER.log(Level.INFO, "SugerenciaProductoResource addProducto: output: {0}", productoDTO);
        return productoDTO;
    }

    
    @GET
    public List<ProductoDTO> getProductos(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId) {
        LOGGER.log(Level.INFO, "SugerenciaProductoResource getProductos: input: {0}", sugerenciasId);
                

        List<ProductoDTO> lista = productoListDTO2Entity(sugerenciaProductoLogic.getProductos(tematicasId, sugerenciasId));
        LOGGER.log(Level.INFO, "SugerenciaProductoResource getBooks: output: {0}", lista.toString());
        return lista;
    }

   
    @GET
    @Path("{producto: [a-zA-Z][a-zA-Z]*}")
    public ProductoDTO getProducto(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId, @PathParam("producto") String productoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SugerenciaProductoResource getProducto: input: sugerenciaId {0} , productoId {1}", new Object[]{sugerenciasId, productoId});
        if (productoLogic.getProducto(productoId) == null) {
            throw new WebApplicationException("El recurso /productos/" + productoId + " no existe.", 404);
        }
       ProductoEntity nuevo = productoLogic.getProducto(productoId);

        ProductoDTO dto = new ProductoDTO(sugerenciaProductoLogic.getProducto(tematicasId,sugerenciasId, nuevo.getId()));
        LOGGER.log(Level.INFO, "SugerenciaProductoResource getProducto: output: {0}", dto);
        return dto;
    }

    
    private List<ProductoDTO> productoListDTO2Entity(List<ProductoEntity> dtos) {
        List<ProductoDTO> list = new ArrayList<>();
        for (ProductoEntity dto : dtos) {
            list.add(new ProductoDTO(dto));
        }
        return list;
    }
}
