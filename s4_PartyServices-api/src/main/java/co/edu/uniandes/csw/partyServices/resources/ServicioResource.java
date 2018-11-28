/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ServicioDTO;
import co.edu.uniandes.csw.partyServices.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.ServicioLogic;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
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
@Path("servicio")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicioResource 
{

  private static final Logger LOGGER = Logger.getLogger(TematicaResource.class.getName());
    
    @Inject
    ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias. 
    
    /**
     * Crea un nuevo servicio con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param servicio {@link ServicioDTO} - EL servicio que se desea guardar.
     * @return JSON {@link ServicioDTO} - El servicio guardado con el atributo id
     * autogenerado.
     */
    @POST
    public ServicioDTO createServicio(ServicioDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource createServicio: input: {0}", servicio);
        ServicioDTO servicioDTO = new ServicioDTO(servicioLogic.createServicio(servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioResource createServicio: output: {0}", servicioDTO);
        return servicioDTO;
    }

    /**
     * Busca y devuelve todos los servicioes que existen en la aplicacion.
     *
     * @return JSONArray {@link ServicioDetailDTO} - Los servicioes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioDetailDTO> getServicios() {
        LOGGER.info("ServicioResource getServicios: input: void");
        List<ServicioDetailDTO> listaServicios = listEntity2DTO(servicioLogic.getServicios());
        LOGGER.log(Level.INFO, "ServicioResource getServicios: output: {0}", listaServicios);
        return listaServicios;
    }

    /**
     * Busca el servicio con el id asociado recibido en la URL y lo devuelve.
     *
     * @param serviciosId Identificador del servicio que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "ServicioResource getServicio: input: {0}", serviciosId);
        ServicioEntity servicioEntity = servicioLogic.getServicio(serviciosId);
        if (servicioEntity == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDetailDTO detailDTO = new ServicioDetailDTO(servicioEntity);
        LOGGER.log(Level.INFO, "ServicioResource getServicio: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el servicio con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param serviciosId Identificador del servicio que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param servicio {@link ServicioDetailDTO} El servicio que se desea guardar.
     * @return JSON {@link ServicioDetailDTO} - El servicio guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio a
     * actualizar.
     */
    @PUT
    @Path("{serviciosId: \\d+}")
    public ServicioDetailDTO updateServicio(@PathParam("serviciosId") Long serviciosId, ServicioDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: input: id: {0} , servicio: {1}", new Object[]{serviciosId, servicio.toString()});
        servicio.setId(serviciosId);
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDetailDTO detailDTO = new ServicioDetailDTO(servicioLogic.updateServicio(serviciosId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el servicio con el id asociado recibido en la URL.
     *
     * @param serviciosId Identificador del servicio que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.proveedorstore.exceptions.BusinessLogicException
     * si el servicio tiene proveedores asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el servicio a borrar.
     */
    @DELETE
    @Path("{serviciosId: \\d+}")
    public void deleteServicio(@PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource deleteServicio: input: {0}", serviciosId);
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        servicioLogic.deleteServicio(serviciosId);
        LOGGER.info("ServicioResource deleteServicio: output: void");
    }

    /**
     * Conexión con el servicio de proveedores para un servicio.
     * {@link ServicioProveedorsResource}
     *
     * Este método conecta la ruta de /servicios con las rutas de /proveedores que
     * dependen del servicio, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los proveedores.
     *
     * @param serviciosId El ID del servicio con respecto al cual se accede al
     * servicio.
     * @return El servicio de Libros para ese servicio en paricular.
     
     * ----------------------------------------------------------------------------------------------------------------------------------------------------
     * ----------------------------------------------------------------------------------------------------------------------------------------------------
    */
    @Path("{serviciosId: \\d+}/proveedores")
    public Class<ServicioProveedoresResource> getServicioProveedoresResource(@PathParam("serviciosId") Long serviciosId) {
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        return ServicioProveedoresResource.class;
    }
    

    /**
     * Convierte una lista de ServicioEntity a una lista de ServicioDetailDTO.
     *
     * @param entityList Lista de ServicioEntity a convertir.
     * @return Lista de ServicioDetailDTO convertida.
     */
    private List<ServicioDetailDTO> listEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDetailDTO> list = new ArrayList<>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDetailDTO(entity));
        }
        return list;
    }
}
