/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ServicioDTO;
import co.edu.uniandes.csw.partyServices.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.ServicioLogic;
import co.edu.uniandes.csw.partyServices.ejb.TematicaServiciosLogic;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
 * @author Tomas Vargas
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TematicaServiciosResource {
    
    private static final Logger LOGGER = Logger.getLogger(TematicaServiciosResource.class.getName());

    @Inject
    private TematicaServiciosLogic tematicaServiciosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un servicio dentro de una tematica con la informacion que recibe el
     * la URL. Se devuelve el servicio que se guarda en la tematica.
     *
     * @param tematicasId Identificador de la tematica que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param serviciosId Identificador del servicio que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ServicioDTO} - El servicio guardado en la tematica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio.
     */
    @POST
    @Path("{serviciosId: \\d+}")
    public ServicioDTO addServicio(@PathParam("tematicasId") Long tematicasId, @PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "TematicaServiciosResource addServicio: input: tematicasID: {0} , serviciosId: {1}", new Object[]{tematicasId, serviciosId});
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDTO servicioDTO = new ServicioDTO(tematicaServiciosLogic.addServicio(serviciosId, tematicasId));
        LOGGER.log(Level.INFO, "TematicaServiciosResource addServicio: output: {0}", servicioDTO);
        return servicioDTO;
    }

    /**
     * Busca y devuelve todos los servicios que existen en la tematica.
     *
     * @param tematicasId Identificador de la tematica que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ServicioDetailDTO} - Los servicios encontrados en la
     * tematica. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioDetailDTO> getServicios(@PathParam("tematicasId") Long tematicasId) {
        LOGGER.log(Level.INFO, "TematicaServiciosResource getServicios: input: {0}", tematicasId);
        List<ServicioDetailDTO> listaDetailDTOs = serviciosListEntity2DTO(tematicaServiciosLogic.getServicios(tematicasId));
        LOGGER.log(Level.INFO, "TematicaServiciosResource getServicios: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el servicio con el id asociado dentro de la tematica con id asociado.
     *
     * @param tematicasId Identificador de la tematica que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param serviciosId Identificador del servicio que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio en la
     * tematica.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("tematicasId") Long tematicasId, @PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TematicaServiciosResource getServicio: input: tematicasID: {0} , serviciosId: {1}", new Object[]{tematicasId, serviciosId});
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /tematicas/" + tematicasId + "/servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDetailDTO servicioDetailDTO = new ServicioDetailDTO(tematicaServiciosLogic.getServicio(tematicasId, serviciosId));
        LOGGER.log(Level.INFO, "TematicaServiciosResource getServicio: output: {0}", servicioDetailDTO);
        return servicioDetailDTO;
    }

    /**
     * Remplaza las instancias de Servicio asociadas a una instancia de Tematica
     *
     * @param tematicasId Identificador de la tematica que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param servicios JSONArray {@link ServicioDTO} El arreglo de servicios nuevo para la
     * tematica.
     * @return JSON {@link ServicioDTO} - El arreglo de servicios guardado en la
     * tematica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio.
     */
    @PUT
    public List<ServicioDetailDTO> replaceServicios(@PathParam("tematicasId") Long tematicasId, List<ServicioDetailDTO> servicios) {
        LOGGER.log(Level.INFO, "TematicaServiciosResource replaceServicios: input: tematicasId: {0} , servicios: {1}", new Object[]{tematicasId, servicios});
        for (ServicioDetailDTO servicio : servicios) {
            if (servicioLogic.getServicio(servicio.getId()) == null) {
                throw new WebApplicationException("El recurso /servicios/" + servicio.getId() + " no existe.", 404);
            }
        }
        List<ServicioDetailDTO> listaDetailDTOs = serviciosListEntity2DTO(tematicaServiciosLogic.replaceServicios(tematicasId, serviciosListDTO2Entity(servicios)));
        LOGGER.log(Level.INFO, "TematicaServiciosResource replaceServicios: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ServicioEntity a una lista de ServicioDetailDTO.
     *
     * @param entityList Lista de ServicioEntity a convertir.
     * @return Lista de ServicioDTO convertida.
     */
    private List<ServicioDetailDTO> serviciosListEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDetailDTO> list = new ArrayList();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ServicioDetailDTO a una lista de ServicioEntity.
     *
     * @param dtos Lista de ServicioDetailDTO a convertir.
     * @return Lista de ServicioEntity convertida.
     */
    private List<ServicioEntity> serviciosListDTO2Entity(List<ServicioDetailDTO> dtos) {
        List<ServicioEntity> list = new ArrayList<>();
        for (ServicioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

