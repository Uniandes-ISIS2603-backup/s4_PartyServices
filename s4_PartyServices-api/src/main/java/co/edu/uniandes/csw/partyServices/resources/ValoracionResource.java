/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ValoracionDTO;
import co.edu.uniandes.csw.partyServices.ejb.ValoracionLogic;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
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
 * Clase que implementa el recurso "Sugerencias"
 *
 * @author Jesús Orlnado Cárcamo Posada
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ValoracionResource {

    private static final Logger LOGGER = Logger.getLogger(ValoracionResource.class.getName());

    @Inject
    private ValoracionLogic valoracionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    private static final String RECURSO_PROVEEDORES = "El recurso /proveedores/";

    private static final String RECURSO_VALORACIONES = "/valoraciones/";

    private static final String NO_EXISTE = " no existe.";

    /**
     * Crea una nueva valoracion con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * @param proveedorId El ID del proveedor del cual se le agrega la
     * valoracion.
     * @param valoracion {@link ValroacionDTO} - La valoracion que se desea
     * guardar.
     * @return JSON {@link ValoracionDTO} - La valoracion guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera si el texto de la valoracion excede el
     * límite establecido (20000 caracteres).
     */
    @POST
    public ValoracionDTO createValoracion(@PathParam("proveedorId") Long proveedorId, ValoracionDTO valoracion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ValoracionResource createValoracion: input: {0}", valoracion);
        ValoracionDTO nuevaValoracionDTO = new ValoracionDTO(valoracionLogic.createValoracion(proveedorId, valoracion.toEntity()));
        LOGGER.log(Level.INFO, "ValoracionResource createValoracion: output: {0}", nuevaValoracionDTO);
        return nuevaValoracionDTO;
    }

    /**
     * Busca y devuelve la valoracion con el ID recibido en la URL, relativa a
     * un proveedor.
     *
     * @param proveedorId El ID del proveedor del cual se buscan las
     * valoraciones.
     * @param valoracionId El ID de la valoracion que se busca.
     * @return {@link ValoracionDTO} - La valoracion encontrada en el proveedor.
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proveedor. Error
     * de lógica que se genera cuando no se encuentra la valoracion.
     */
    @GET
    @Path("{valoracionesId: \\d+}")
    public ValoracionDTO getValoracion(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionesId") Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ValoracionResource getValoracion: input: {0}", valoracionId);
        ValoracionEntity entity = valoracionLogic.getValoracion(proveedorId, valoracionId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_PROVEEDORES + proveedorId + RECURSO_VALORACIONES + valoracionId + NO_EXISTE, 404);
        }
        ValoracionDTO valoracionDTO = new ValoracionDTO(entity);
        LOGGER.log(Level.INFO, "ValoracionResource getValoracion: output: {0}", valoracionDTO);
        return valoracionDTO;
    }

    /**
     * Busca y devuelve todas las valroaciones que existen en un proveedor.
     *
     * @param proveedorId El ID del proveedor del cual se buscan las
     * valoraciones.
     * @return JSONArray {@link ValoracionDTO} - Las valoraciones encontradas en
     * el proveedor. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ValoracionDTO> getValoraciones(@PathParam("proveedorId") Long proveedorId) {
        LOGGER.log(Level.INFO, "ValoracionResource getValoraciones: input: {0}", proveedorId);
        List<ValoracionDTO> listaDTOs = listEntity2DTO(valoracionLogic.getValoraciones(proveedorId));
        LOGGER.log(Level.INFO, "ValoracionResource getValoraciones: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Actualiza una valoracion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa el objeto actualizado.
     *
     * @param proveedorId El ID del proveedor del cual se buscan las
     * valoraciones.
     * @param valoracionId El ID de la valoracion que se va a actualizar.
     * @param valoracion {@link ValoracionDTO} - La valroacion que se desea
     * guardar.
     * @return JSON {@link ValoracionDTO} - La valoracion actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando los caracteres de la valoracion
     * exceden el límite permitido (20000) o el valoracionId y el ID de la
     * valoracion no coinciden.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la valoracion.
     */
    @PUT
    @Path("{valoracionesId: \\d+}")
    public ValoracionDTO updateValoracion(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionesId") Long valoracionId, ValoracionDTO valoracion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ValoracionResource updateValoracion: input: proveedorId: {0} , valoracionId: {1} , valoracion:{2}", new Object[]{proveedorId, valoracionId, valoracion});

        valoracion.setId(valoracionId);
        ValoracionEntity entity = valoracionLogic.getValoracion(proveedorId, valoracionId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_PROVEEDORES + proveedorId + RECURSO_VALORACIONES + valoracionId + NO_EXISTE, 404);

        }
        ValoracionDTO valoracionDTO = new ValoracionDTO(valoracionLogic.updateValoracion(proveedorId, valoracion.toEntity()));
        LOGGER.log(Level.INFO, "ValoracionResource updateValoracion: output:{0}", valoracion);
        return valoracionDTO;
    }

    /**
     * Borra la valoracion con el id asociado recibido en la URL.
     *
     * @param proveedorId El ID del proveedor del cual se va a eliminar la
     * valoracion.
     * @param valoracionId El ID de la valoracion que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la valoracion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la valoracion.
     */
    @DELETE
    @Path("{valoracionesId: \\d+}")
    public void deleteValoracion(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionesId") Long valoracionId) throws BusinessLogicException {
        ValoracionEntity entity = valoracionLogic.getValoracion(proveedorId, valoracionId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_PROVEEDORES + proveedorId + RECURSO_VALORACIONES + valoracionId + NO_EXISTE, 404);
        }
        valoracionLogic.deleteValoracion(proveedorId, valoracionId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ValoracionEntity a una lista
     * de objetos ValoracionDTO (json)
     *
     * @param entityList corresponde a la lista de valoraciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de valoraciones en forma DTO (json)
     */
    private List<ValoracionDTO> listEntity2DTO(List<ValoracionEntity> entityList) {
        List<ValoracionDTO> list = new ArrayList<>();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDTO(entity));
        }
        return list;
    }

}
