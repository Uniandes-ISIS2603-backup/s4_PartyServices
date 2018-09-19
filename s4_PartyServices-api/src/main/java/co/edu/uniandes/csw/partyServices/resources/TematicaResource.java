/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.TematicaDTO;
import co.edu.uniandes.csw.partyServices.ejb.TematicaLogic;
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
     * Busca la tematica con el id asociado recibido en la URL y la devuelve.
     *
     * @param tematicasId Identificador de la tematica que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link TematicaDTO} - La tematica buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tematica.
     */
    @GET
    @Path("{tematicasId: \\d+}")
    public TematicaDTO getTematica(@PathParam("tematicasId") Long tematicasId) throws WebApplicationException {
        return new TematicaDTO();
    }
 
    /**
     * Crea una nueva tematica con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param tematica {@link TematicaDTO} - La tematica que se desea
     * guardar.
     * @return JSON {@link TematicaDTO} - La tematica guardada con el atributo
     * id autogenerado.
     */
    @POST
    public TematicaDTO crearTematica(TematicaDTO tematica) throws WebApplicationException{
        return tematica;
    }
    
    /**
     * Modifica tematica con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico.
     *
     * @param tematicasId Identificador de la tematica que se desea modificar.
     * @param tematica {@link TematicaDTO} - La tematica que se desea
     * modificar.
     * @return JSON {@link TematicaDTO} - La tematica modificada.
     */
    @PUT
    @Path("{tematicasId: \\d+}")
    public TematicaDTO modificarTematica(@PathParam("tematicasId") Long tematicasId, TematicaDTO nuevaTematica) throws WebApplicationException{
        return nuevaTematica;
    }
    
     /**
     * Borra la tematica con el id asociado recibido en la URL.
     *
     * @param tematicasId Identificador de la tematica que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{tematicasId: \\d+}")
    public void deleteTematica(@PathParam("tematicasId") Long tematicasId) throws WebApplicationException {
        
    }
    
    /**
     * Conexión con el servicio de sugerencias para una temática. {@link SugerenciaResource}
     *
     * Este método conecta la ruta de /tematicas con las rutas de /sugerencias que
     * dependen de la temática, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las sugerencias.
     *
     * @param tematicasId El ID de la temática con respecto a la cual se accede al
     * servicio.
     * @return El servicio de Sugerencias para esa temática en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la temática.
     */
    @Path("{tematicasId: \\d+}/sugerencias")
    public Class<SugerenciaResource> getSugerenciaResource(@PathParam("tematicasId") Long tematicasId) {
        if (tematicaLogic.getTematica(tematicasId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias no existe.", 404);
        }
        return SugerenciaResource.class;
    }
}
