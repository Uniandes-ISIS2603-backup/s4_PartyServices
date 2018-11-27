/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.TematicaDTO;
import co.edu.uniandes.csw.partyServices.dtos.TematicaDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.TematicaLogic;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
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

/**
 *
 * @author Tomas Vargas
 */
@Path("tematicas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TematicaResource {
    
    private static final Logger LOGGER = Logger.getLogger(TematicaResource.class.getName());
    
    @Inject
    TematicaLogic tematicaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias. */
    
    /**
     * Crea una nueva tematica con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param tematica {@link TematicaDTO} - La tematica que se desea
     * guardar.
     * @return JSON {@link TematicaDTO} - La tematica guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la tematica.
     */
    @POST
    public TematicaDTO createTematica(TematicaDTO tematica) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TematicaResource createTematica: input: {0}", tematica.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        TematicaEntity tematicaEntity = tematica.toEntity();
        // Invoca la lógica para crear la tematica nueva
        TematicaEntity nuevoTematicaEntity = tematicaLogic.createTematica(tematicaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        TematicaDTO nuevoTematicaDTO = new TematicaDTO(nuevoTematicaEntity);
        LOGGER.log(Level.INFO, "TematicaResource createTematica: output: {0}", nuevoTematicaDTO.toString());
        return nuevoTematicaDTO;
    }

    /**
     * Busca y devuelve todas las tematicaes que existen en la aplicacion.
     *
     * @return JSONArray {@link TematicaDetailDTO} - Las tematicaes
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<TematicaDetailDTO> getTematicas() {
        LOGGER.info("TematicaResource getTematicas: input: void");
        List<TematicaDetailDTO> listaTematicaes = listEntity2DetailDTO(tematicaLogic.getTematicas());
        LOGGER.log(Level.INFO, "TematicaResource getTematicas: output: {0}", listaTematicaes.toString());
        return listaTematicaes;
    }

    /**
     * Busca la tematica con el id asociado recibido en la URL y la devuelve.
     *
     * @param tematicasId Identificador de la tematica que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link TematicaDetailDTO} - La tematica buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica.
     */
    @GET
    @Path("{tematicasId: \\d+}")
    public TematicaDetailDTO getTematica(@PathParam("tematicasId") Long tematicasId) throws WebApplicationException {
        
        LOGGER.log(Level.INFO, "TematicaResource getTematica: input: {0}", tematicasId);
        TematicaEntity tematicaEntity = tematicaLogic.getTematica(tematicasId);
        if (tematicaEntity == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + " no existe.", 404);
        }
        TematicaDetailDTO detailDTO = new TematicaDetailDTO(tematicaEntity);
        LOGGER.log(Level.INFO, "TematicaResource getTematica: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la tematica con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param tematicasId Identificador de la tematica que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param tematica {@link TematicaDetailDTO} La tematica que se desea
     * guardar.
     * @return JSON {@link TematicaDetailDTO} - La tematica guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica a
     * actualizar.
     */
    @PUT
    @Path("{tematicasId: \\d+}")
    public TematicaDetailDTO updateTematica(@PathParam("tematicasId") Long tematicasId, TematicaDetailDTO tematica) throws WebApplicationException {
        LOGGER.log(Level.INFO, "TematicaResource updateTematica: input: id:{0} , tematica: {1}", new Object[]{tematicasId, tematica.toString()});
        tematica.setId(tematicasId);
        if (tematicaLogic.getTematica(tematicasId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + " no existe.", 404);
        }
        TematicaDetailDTO detailDTO = new TematicaDetailDTO(tematicaLogic.updateTematica(tematicasId, tematica.toEntity()));
        LOGGER.log(Level.INFO, "TematicaResource updateTematica: output: {0}", detailDTO.toString());
        return detailDTO;

    }

    /**
     * Borra la tematica con el id asociado recibido en la URL.
     *
     * @param tematicasId Identificador de la tematica que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la tematica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica.
     */
    @DELETE
    @Path("{tematicasId: \\d+}")
    public void deleteTematica(@PathParam("tematicasId") Long tematicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TematicaResource deleteTematica: input: {0}", tematicasId);
        if (tematicaLogic.getTematica(tematicasId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + " no existe.", 404);
        }
        tematicaLogic.deleteTematica(tematicasId);
        LOGGER.info("TematicaResource deleteTematica: output: void");
    }

    /**
     * Conexión con el servicio de Servicios para una tematica.
     * {@link TematicaServiciosResource}
     *
     * Este método conecta la ruta de /tematicas con las rutas de /servicios que
     * dependen de la tematica, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los Servicios de una tematica.
     *
     * @param tematicasId El ID de la tematica con respecto a la cual se
     * accede al servicio.
     * @return El servicio de Servicios para esta tematica en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica.
     
     * -------------------------------------------------------------------------------------------------------------------------------------------------------
    @Path("{tematicasId: \\d+}/servicios")
    public Class<TematicaServiciosResource> getTematicaServiciosResource(@PathParam("tematicasId") Long tematicasId) {
        if (tematicaLogic.getTematica(tematicasId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + " no existe.", 404);
        }
        return TematicaServiciosResource.class;
    }
    */

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos TematicaEntity a una lista de
     * objetos TematicaDetailDTO (json)
     *
     * @param entityList corresponde a la lista de tematicaes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de tematicaes en forma DTO (json)
     */
    private List<TematicaDetailDTO> listEntity2DetailDTO(List<TematicaEntity> entityList) {
        List<TematicaDetailDTO> list = new ArrayList<>();
        for (TematicaEntity entity : entityList) {
            list.add(new TematicaDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Conexión con el servicio de sugerencias para una temática. {@link TematicaResource}
     *
     * Este método conecta la ruta de /tematicas con las rutas de /sugerencias que
     * dependen de la tematica, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las sugerencias.
     *
     * @param tematicasId El ID de la tematica con respecto a la cual se accede al
     * servicio.
     * @return El servicio de sugerencias para esa tematica en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica.
     */
    @Path("{tematicasId: \\d+}/sugerencias")
    public Class<SugerenciaResource> getSugerenciaResource(@PathParam("tematicasId") Long tematicasId) {
        if (tematicaLogic.getTematica(tematicasId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias no existe.", 404);
        }
        return SugerenciaResource.class;
    }
}
