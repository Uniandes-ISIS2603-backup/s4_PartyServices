/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ClienteDTO;
import co.edu.uniandes.csw.partyServices.ejb.SugerenciaClienteLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *Clase que implementa el recurso "tematicas/{id}/sugerencias/{id}/cliente".
 * 
 * @author Jesús Orlando Cárcamo Posada
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SugerenciaClienteResource {
    /**
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaClienteResource.class.getName());
    
    @Inject
    private SugerenciaClienteLogic sugerenciaClienteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /*@Inject
    private ClienteLogic clienteLogic; */// Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.


/**
 * Guarda un cliente dentro de una sugerencia con la informacion que recibe el la
 * URL.
 *
 * @param tematicasId El ID de la temática de la cual se le agrega la sugerencia.
 * @param sugerenciasId Identificador de la sugerencia que se esta actualizando. Este
 * debe ser una cadena de dígitos.
 * @param clientesId Identificador del cliente que se desea guardar. Este debe
 * ser una cadena de dígitos.
 * @return JSON {@link ClienteDTO} - El cliente guardado en la sugerencia.
 * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
 * Error de lógica que se genera cuando no se encuentra el cliente.
 
    @POST
    @Path("{sugerenciasId: \\d+}")
    public ClienteDTO addCliente(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId, @PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "SugerenciaClienteResource addCliente: input: tematicasId: {2}, sugerenciasId: {0} , clientesId: {1}", new Object[]{sugerenciasId, clientesId, tematicasId});
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        ClienteDTO clienteDTO = new ClienteDTO(sugerenciaClienteLogic.addCliente(tematicasId, sugerenciasId, clientesId));
        LOGGER.log(Level.INFO, "SugerenciaClienteResource addCliente: output: {0}", clienteDTO.toString());
        return clienteDTO;
    }
    
    /**
     * Busca el cliente dentro de la sugerencia con id asociado.
     *
     * @param sugerenciasId Identificador de la sugerencia que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param tematicasId El ID de la temática de la cual se le agrega la sugerencia.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando la sugerencia no tiene cliente.
     
    @GET
    public ClienteDetailDTO getCliente(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId) {
        LOGGER.log(Level.INFO, "SugerenciaClienteResource getCliente: input: tematicasId: {0}, sugerenciasId{1}", new Object[]{tematicasId, sugerenciasId});
        ClienteEntity clienteEntity = sugerenciaClienteLogic.getCliente(tematicasId,sugerenciasId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/sugerencias/" + sugerenciasId + "/cliente no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "SugerenciaClienteResource getCliente: output: {0}", clienteDetailDTO.toString());
        return clienteDetailDTO;
    }
    
    /**
     * Remplaza la instancia de cliente asociada a una instancia de sugerencia.
     *
     * @param sugerenciasId Identificador de la sugerencia que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param tematicasId El ID de la temática de la cual se le agrega la sugerencia.
     * @param clientesId Identificador del cliente que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO replaceCliente(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId, @PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "SugerenciaClienteResource replaceCliente: input: tematicasId: {2}, sugerenciasId: {0} , clientesId: {1}", new Object[]{sugerenciasId, clientesId, tematicasId});
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(sugerenciaClienteLogic.replaceCliente(tematicasId, sugerenciasId, clientesId));
        LOGGER.log(Level.INFO, "SugerenciaClienteResource replacecliente: output: {0}", authorDetailDTO.toString());
        return clienteDetailDTO;
    }
    
    /**
     * Elimina la conexión entre el cliente y la sugerencia recibida en la URL.
     *
     * @param sugerenciasId El ID de la sugerencia a la cual se le va a desasociar el cliente.
     * @param tematicasId El ID de la temática de la cual se le agrega la sugerencia.
     * @throws BusinessLogicException
     * Error de lógica que se genera cuando el premio no tiene autor.
     
    @DELETE
    public void removeAuthor(@PathParam("tematicasId") Long tematicasId, @PathParam("sugerenciasId") Long sugerenciasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SugerenciaClienteResource removeCliente: input: input: tematicasId: {0}, sugerenciasId{1}", new Object[]{tematicasId, sugerenciasId});
        sugerenciaClienteLogic.removeCliente(tematicasId, sugerenciasId);
        LOGGER.info("SugerenciaClienteResource removeCliente: output: void");
    }
      */
}

