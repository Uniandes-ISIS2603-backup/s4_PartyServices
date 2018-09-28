/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ValoracionDTO;
import co.edu.uniandes.csw.partyServices.ejb.ClienteValoracionesLogic;
import co.edu.uniandes.csw.partyServices.ejb.ValoracionLogic;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteValoracionesResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteValoracionesResource.class.getName());

    @Inject
    private ClienteValoracionesLogic clienteValoracionesLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ValoracionLogic valoracionLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda una valoracion dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve la valoracion que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param valoracionId Identificador de la valoracion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @param proveedorId. Identificador del proveedor del cual pertenece la valoracion.
     * @return JSON {@link ValoracionDTO} - La valoracion guardada en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la valoracion.
     */
    @POST
    @Path("{proveedorId: \\d+}/valoraciones/{valoracionId: \\d+}")
    public ValoracionDTO addValoracion(@PathParam("clientesId") Long clientesId, @PathParam("proveedorId") Long proveedorId, @PathParam("valoracionId") Long valoracionId){
        LOGGER.log(Level.INFO, "ClienteValoracionesResource addValoracion: input: clientesId: {0} , proveedorId: {1}, valoracionId: {2}", new Object[]{clientesId, proveedorId, valoracionId});
        if (valoracionLogic.getValoracion(proveedorId, valoracionId) == null) {
            throw new WebApplicationException("El recurso /valoraciones/" + valoracionId + " no existe.", 404);
        }
        ValoracionDTO valoracionDTO = new ValoracionDTO(clienteValoracionesLogic.addValoracion(proveedorId, valoracionId, clientesId));
        LOGGER.log(Level.INFO, "ClienteValoracionesResource addValoracion: output: {0}", valoracionDTO.toString());
        return valoracionDTO;
    }
    
    /**
     * Busca y devuelve todos las valoraciones que existen en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SugerenciaDTO} - Las valoraciones encontradas en el
     * cliente. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    @Path("todos/valoraciones")
    public List<ValoracionDTO> getValoraciones(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteValoracionesResource getValoraciones: input: {0}", clientesId);
        List<ValoracionDTO> listaDTOs = valoracionListEntity2DTO(clienteValoracionesLogic.getValoraciones(clientesId));
        LOGGER.log(Level.INFO, "ClienteValoracionesResource getValoraciones: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    /**
     * Busca la valoracion con el id asociado dentro del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param valoracionId Identificador de la valoracion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param proveedorId. Identificador del proveedor del cual hace parte la valoracion. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ValoracionDTO} - La valoracion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la valoracion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la valoracion en el
     * cliente.
     */
    @GET
    @Path("{proveedorId: \\d+}/valoraciones/{valoracionId: \\d+}")
    public ValoracionDTO getValoracion (@PathParam("clientesId") Long clientesId, @PathParam("valoracionId") Long valoracionId, @PathParam("proveedorId") Long proveedorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteValoracionesResource getValoracion: input: clientesId: {0} , valoracionId: {1}, proveedorId: {2}", new Object[]{clientesId, valoracionId, proveedorId});
        if (valoracionLogic.getValoracion(proveedorId, valoracionId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/proveedor/" + proveedorId + "/valoraciones/"+ valoracionId + " no existe.", 404);
        }
        ValoracionDTO valoracionDTO = new ValoracionDTO(clienteValoracionesLogic.getValoracion(proveedorId, valoracionId, clientesId));
        LOGGER.log(Level.INFO, "\"ClienteValoracionesResource getValoracion: output: {0}", valoracionDTO.toString());
        return valoracionDTO;
    }
    
    /**
     * Remueve la relación entre un cliente y sus sugerencias.
     *
     * @param clientesId. El ID del cliente que tiene las sugerencias a las que se quiere desasociar.
     */
    @DELETE
    @Path("todos/valoraciones")
    public void removeValoraciones(@PathParam("clientesId") Long clientesId){
        LOGGER.log(Level.INFO, "ClienteValoracionesResource removeValoraciones: input: clientesId: {0}", clientesId);
        clienteValoracionesLogic.removeValoraciones(clientesId);
        LOGGER.log(Level.INFO, "ClienteValoracionesResource removeValoraciones: output: clientesId: {0}", clientesId);
    }
    
    /**
     * Convierte una lista de ValoracionEntity a una lista de ValoracionDTO.
     *
     * @param entityList Lista de ValoracionEntity a convertir.
     * @return Lista de ValoracionDTO convertida.
     */
    private List<ValoracionDTO> valoracionListEntity2DTO(List<ValoracionEntity> entityList) {
        List<ValoracionDTO> list = new ArrayList();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDTO(entity));
        }
        return list;
    }
}
