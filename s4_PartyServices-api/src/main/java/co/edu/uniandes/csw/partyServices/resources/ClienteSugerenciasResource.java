/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.SugerenciaDTO;
import co.edu.uniandes.csw.partyServices.ejb.ClienteSugerenciasLogic;
import co.edu.uniandes.csw.partyServices.ejb.SugerenciaLogic;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "tematicas/{id}/sugerencias/{id}/cliente".
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteSugerenciasResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteSugerenciasResource.class.getName());

    @Inject
    private ClienteSugerenciasLogic clienteSugerenciaLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private SugerenciaLogic sugerenciaLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda una sugerencia dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve la sugerencia que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param sugerenciasId Identificador de la sugerencia que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @param tematicasId. Identificador de la temática de la cual pertenece la sugerencia.
     * @return JSON {@link SugerenciaDTO} - La sugerencia guardada en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sugerencia.
     */
    @POST
    @Path("{tematicasId: \\d+}/sugerencias/{sugerenciasId: \\d+}")
    public SugerenciaDTO addSugerencia(@PathParam("clientesId") Long clientesId, @PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId){
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource addSugerencia: input: clientesId: {0} , tematicasId: {1}, sugerenciasId: {2}", new Object[]{clientesId, tematicasId, sugerenciasId});
        if (sugerenciaLogic.getSugerencia(tematicasId, sugerenciasId) == null) {
            throw new WebApplicationException("El recurso /sugerencias/" + sugerenciasId + " no existe.", 404);
        }
        SugerenciaDTO sugerenciaDTO = new SugerenciaDTO(clienteSugerenciaLogic.addSugerencia(tematicasId, sugerenciasId, clientesId));
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource addSugerencia: output: {0}", sugerenciaDTO.toString());
        return sugerenciaDTO;
    }
    
    /**
     * Busca y devuelve todos las sugerencias que existen en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SugerenciaDTO} - Las sugerencias encontradas en el
     * cliente. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    @Path("todas/sugerencias")
    public List<SugerenciaDTO> getSugerencias(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource getSugerencias: input: {0}", clientesId);
        List<SugerenciaDTO> listaDTOs = sugerenciaListEntity2DTO(clienteSugerenciaLogic.getSugerencias(clientesId));
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource getSugerencias: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    /**
     * Busca la sugerencia con el id asociado dentro del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param sugerenciasId Identificador de la sugerencia que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param tematicasId. Identificador de la temática de la cual hace parte la sugerencia. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SugerenciaDTO} - La sugerencia buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sugerencia.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sugerencia en el
     * cliente.
     */
    @GET
    @Path("{tematicasId: \\d+}/sugerencias/{sugerenciasId: \\d+}")
    public SugerenciaDTO getSugerencia (@PathParam("clientesId") Long clientesId, @PathParam("sugerenciasId") Long sugerenciasId, @PathParam("tematicasId") Long tematicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource getSugerencia: input: clientesId: {0} , sugerenciasId: {1}, tematicasId: {2}", new Object[]{clientesId, sugerenciasId, tematicasId});
        if (sugerenciaLogic.getSugerencia(tematicasId, sugerenciasId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tematicas/" + tematicasId + "/sugerencias/"+ sugerenciasId + " no existe.", 404);
        }
        SugerenciaDTO sugerenciaDTO = new SugerenciaDTO(clienteSugerenciaLogic.getSugerencia(tematicasId, sugerenciasId, clientesId));
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource getSugerencia: output: {0}", sugerenciaDTO.toString());
        return sugerenciaDTO;
    }
    
    /**
     * Remueve la relación entre un cliente y sus sugerencias.
     *
     * @param clientesId. El ID del cliente que tiene las sugerencias a las que se quiere desasociar.
     */
    @DELETE
    @Path("todas/sugerencias")
    public void removeSugerencias(@PathParam("clientesId") Long clientesId){
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource removeSugerencias: input: clientesId: {0}", clientesId);
        clienteSugerenciaLogic.removeSugerencias(clientesId);
        LOGGER.log(Level.INFO, "ClienteSugerenciaResource removeSugerencias: output: clientesId: {0}", clientesId);
    }
    
    /**
     * Convierte una lista de SugerenciaEntity a una lista de SugerenciaDTO.
     *
     * @param entityList Lista de SugerenciaEntity a convertir.
     * @return Lista de SugerenciaDTO convertida.
     */
    private List<SugerenciaDTO> sugerenciaListEntity2DTO(List<SugerenciaEntity> entityList) {
        List<SugerenciaDTO> list = new ArrayList();
        for (SugerenciaEntity entity : entityList) {
            list.add(new SugerenciaDTO(entity));
        }
        return list;
    }

}
