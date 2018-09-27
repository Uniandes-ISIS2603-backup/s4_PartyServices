/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.SugerenciaDTO;
import co.edu.uniandes.csw.partyServices.ejb.SugerenciaLogic;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *Clase que implementa el recurso "Sugerencias"
 * @author Jesús Orlnado Cárcamo Posada
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SugerenciaResource {
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaResource.class.getName());
    
    @Inject
    private SugerenciaLogic sugerenciaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea una nueva sugerencia con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param tematicasId El ID de la temática de la cual se le agrega la sugerencia.
     * @param sugerencia {@link SugerenciaDTO} - La sugerencia que se desea guardar.
     * @return JSON {@link SugerenciaDTO} - La sugerencia guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera si el texto de la sugerencia excede el límite establecido (20000 caracteres).
     */
    @POST
    public SugerenciaDTO createSugerencia(@PathParam("tematicasId") Long tematicasId, SugerenciaDTO sugerencia) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "SugerenciaResource createSugerencia: input: {0}", sugerencia.toString());
        SugerenciaDTO nuevaSugerenciaDTO = new SugerenciaDTO(sugerenciaLogic.createSugerencia(tematicasId, sugerencia.toEntity()));
        LOGGER.log(Level.INFO, "SugerenciaResource createSugerencia: output: {0}", nuevaSugerenciaDTO.toString());
        return nuevaSugerenciaDTO;
    }
      
    /**
     * Busca y devuelve la sugerencia con el ID recibido en la URL, relativa a una
     * temática.
     *
     * @param tematicasId El ID de la temática de la cual se buscan las sugerencias.
     * @param sugerenciasId El ID de la sugerencia que se busca.
     * @return {@link SugerenciaDTO} - La sugerencia encontrada en la temática.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la temática.
     * Error de lógica que se genera cuando no se encuentra la sugerencia.
     */
    @GET
    @Path("{sugerenciasId: \\d+}")
    public SugerenciaDTO getSugerencia(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "SugerenciaResource getSugerencia: input: {0}", sugerenciasId);
        SugerenciaEntity entity = sugerenciaLogic.getSugerencia(tematicasId, sugerenciasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias/" + sugerenciasId + " no existe.", 404);
        }
        SugerenciaDTO sugerenciaDTO = new SugerenciaDTO(entity);
        LOGGER.log(Level.INFO, "SugerenciaResource getSugerencia: output: {0}", sugerenciaDTO.toString());
        return sugerenciaDTO;
    }
      
    /**
     * Busca y devuelve todas las sugerencias que existen en una temática.
     *
     * @param tematicasId El ID de la temática de la cual se buscan las sugerencias.
     * @return JSONArray {@link SugerenciaDTO} - Las sugerencias encontradas en e la
     * temática. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SugerenciaDTO> getSugerencias(@PathParam("tematicasId") Long tematicasId) {
        LOGGER.log(Level.INFO, "SugerenciaResource getSugerencias: input: {0}", tematicasId);
        List<SugerenciaDTO> listaDTOs = listEntity2DTO(sugerenciaLogic.getSugerencias(tematicasId));
        LOGGER.log(Level.INFO, "SugerenciaResource getSugerencias: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
     
    /**
     * Actualiza una sugerencia con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param tematicasId El ID de la temática de la cual se guarda la sugerencia.
     * @param sugerenciasId El ID de la sugerencia que se va a actualizar.
     * @param sugerencia {@link SugerenciaDTO} - La sugerencia que se desea guardar.
     * @return JSON {@link SugerenciaTO} - La sugerencia actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando los caracteres de la sugerencia exceden el límite permitido (20000) o el sugerenciasId y el ID de la sugerencia no coinciden.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sugerencia.
     */
    @PUT
    @Path("{sugerenciasId: \\d+}")
    public SugerenciaDTO updateSugerencia(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId, SugerenciaDTO sugerencia) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SugerenciaResource updateSugerencia: input: tematicasId: {0} , sugerenciasId: {1} , sugerencia:{2}", new Object[]{tematicasId, sugerenciasId, sugerencia.toString()});
        
        SugerenciaEntity entity = sugerenciaLogic.getSugerencia(tematicasId, sugerenciasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias/" + sugerenciasId + " no existe.", 404);

        }
        SugerenciaDTO sugerenciaDTO = new SugerenciaDTO(sugerenciaLogic.updateSugerencia(tematicasId, sugerencia.toEntity()));
        LOGGER.log(Level.INFO, "SugerenciaResource updateSugerencia: output:{0}", sugerenciaDTO.toString());
        return sugerenciaDTO;
    }
    
    /**
     * Borra la sugerencia con el id asociado recibido en la URL.
     *
     * @param tematicasId El ID de la temática de la cual se va a eliminar la sugerencia.
     * @param sugerenciasId El ID de la sugerencia que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{sugerenciasId: \\d+}")
    public void deleteSugerencia(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId) throws BusinessLogicException {
        SugerenciaEntity entity = sugerenciaLogic.getSugerencia(tematicasId, sugerenciasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias/" + sugerenciasId + " no existe.", 404);
        }
        sugerenciaLogic.deleteSugerencia(tematicasId, sugerenciasId);
    }
    
    /**
     * Conexión con el servicio de clientes para una sugerencia.
     * {@link SugerenciaClienteResource}
     *
     * Este método conecta la ruta de /sugerencias con las rutas de /clientes que
     * dependen de la sugerencia, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga del cliente de la sugerencia.
     *
     * @param sugerenciasId El ID de la sugerencia con respecto a la cual se accede al
     * servicio.
     * @return El servicio de cliente para esta sugerencia en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no existe la sugerencia.
     */
   /* @Path("{tematicasId: \\d+}/{sugerenciasId: \\d+}/cliente")
    public Class<SugerenciaClienteResource> getSugerenciaClienteResource(@PathParam("prizesId") Long prizesId) {
        if (prizeLogic.getPrize(prizesId) == null) {
            throw new WebApplicationException("El recurso /prizes/" + prizesId + " no existe.", 404);
        }
        return PrizeAuthorResource.class;
    }*/
    
    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SugerenciaEntity a una lista de
     * objetos SugerenciaTO (json)
     *
     * @param entityList corresponde a la lista de sugerencias de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de sugerencias en forma DTO (json)
     */
    private List<SugerenciaDTO> listEntity2DTO(List<SugerenciaEntity> entityList) {
        List<SugerenciaDTO> list = new ArrayList<>();
        for (SugerenciaEntity entity : entityList) {
            list.add(new SugerenciaDTO(entity));
        }
        return list;
    }
    
}
