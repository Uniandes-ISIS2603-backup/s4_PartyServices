/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProveedorDTO;
import co.edu.uniandes.csw.partyServices.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.ProveedorLogic;
import co.edu.uniandes.csw.partyServices.ejb.ServicioProveedoresLogic;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
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
 * @author Tomas Vargas
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicioProveedoresResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioProveedoresResource.class.getName());

    @Inject
    private ServicioProveedoresLogic servicioProveedoresLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ProveedorLogic proveedorLogic; //Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda un Proveedor dentro de una servicio con la informacion que recibe el
     * la URL. Se devuelve el Proveedor que se guarda en la servicio.
     *
     * @param serviciosId Identificador de la servicio que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param proveedoresId Identificador del Proveedor que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ProveedorDTO} - El Proveedor guardado en la servicio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el Proveedor.
     */
    @POST
    @Path("{proveedoresId: \\d+}")
    public ProveedorDTO addProveedor(@PathParam("serviciosId") Long serviciosId, @PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "ServicioProveedoresResource addProveedor: input: serviciosID: {0} , proveedoresId: {1}", new Object[]{serviciosId, proveedoresId});
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + proveedoresId + " no existe.", 404);
        }
        ProveedorDTO proveedorDTO = new ProveedorDTO(servicioProveedoresLogic.addProveedor(proveedoresId, serviciosId));
        LOGGER.log(Level.INFO, "ServicioProveedoresResource addProveedor: output: {0}", proveedorDTO);
        return proveedorDTO;
    }

    /**
     * Busca y devuelve todos los Proveedores que existen en la servicio.
     *
     * @param serviciosId Identificador de la servicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ProveedorDetailDTO} - Los Proveedores encontrados en la
     * servicio. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProveedorDetailDTO> getProveedores(@PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "ServicioProveedoresResource getProveedores: input: {0}", serviciosId);
        List<ProveedorDetailDTO> listaDetailDTOs = proveedoresListEntity2DTO(servicioProveedoresLogic.getProveedores(serviciosId));
        LOGGER.log(Level.INFO, "ServicioProveedoresResource getProveedores: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el Proveedor con el id asociado dentro de la servicio con id asociado.
     *
     * @param serviciosId Identificador de la servicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param proveedoresId Identificador del Proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ProveedorDetailDTO} - El Proveedor buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el Proveedor.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el Proveedor en la
     * servicio.
     */
    @GET
    @Path("{proveedoresId: \\d+}")
    public ProveedorDetailDTO getProveedor(@PathParam("serviciosId") Long serviciosId, @PathParam("proveedoresId") Long proveedoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioProveedoresResource getProveedor: input: serviciosID: {0} , proveedoresId: {1}", new Object[]{serviciosId, proveedoresId});
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + "/proveedores/" + proveedoresId + " no existe.", 404);
        }
        ProveedorDetailDTO proveedorDetailDTO = new ProveedorDetailDTO(servicioProveedoresLogic.getProveedor(serviciosId, proveedoresId));
        LOGGER.log(Level.INFO, "ServicioProveedoresResource getProveedor: output: {0}", proveedorDetailDTO);
        return proveedorDetailDTO;
    }

    /**
     * Remplaza las instancias de Proveedor asociadas a una instancia de Servicio
     *
     * @param serviciosId Identificador de la servicio que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param proveedores JSONArray {@link ProveedorDTO} El arreglo de Proveedores nuevo para la
     * servicio.
     * @return JSON {@link ProveedorDTO} - El arreglo de Proveedores guardado en la
     * servicio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el Proveedor.
     */
    @PUT
    public List<ProveedorDetailDTO> replaceProveedores(@PathParam("serviciosId") Long serviciosId, List<ProveedorDetailDTO> proveedores) {
        LOGGER.log(Level.INFO, "ServicioProveedoresResource replaceProveedores: input: serviciosId: {0} , proveedores: {1}", new Object[]{serviciosId, proveedores});
        for (ProveedorDetailDTO proveedor : proveedores) {
            if (proveedorLogic.getProveedor(proveedor.getId()) == null) {
                throw new WebApplicationException("El recurso /proveedores/" + proveedor.getId() + " no existe.", 404);
            }
        }
        List<ProveedorDetailDTO> listaDetailDTOs = proveedoresListEntity2DTO(servicioProveedoresLogic.replaceProveedores(serviciosId, proveedoresListDTO2Entity(proveedores)));
        LOGGER.log(Level.INFO, "ServicioProveedoresResource replaceProveedores: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ProveedorEntity a una lista de ProveedorDetailDTO.
     *
     * @param entityList Lista de ProveedorEntity a convertir.
     * @return Lista de ProveedorDTO convertida.
     */
    private List<ProveedorDetailDTO> proveedoresListEntity2DTO(List<ProveedorEntity> entityList) {
        List<ProveedorDetailDTO> list = new ArrayList();
        for (ProveedorEntity entity : entityList) {
            list.add(new ProveedorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ProveedorDetailDTO a una lista de ProveedorEntity.
     *
     * @param dtos Lista de ProveedorDetailDTO a convertir.
     * @return Lista de ProveedorEntity convertida.
     */
    private List<ProveedorEntity> proveedoresListDTO2Entity(List<ProveedorDetailDTO> dtos) {
        List<ProveedorEntity> list = new ArrayList<>();
        for (ProveedorDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}
