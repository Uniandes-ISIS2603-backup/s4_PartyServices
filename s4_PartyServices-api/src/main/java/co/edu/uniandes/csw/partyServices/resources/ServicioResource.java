/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ServicioDTO;
import co.edu.uniandes.csw.partyServices.dtos.TematicaDTO;
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
    
    //@Inject
    /* ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias. */
    
    /**
     * Busca el servicio con el id asociado recibido en la URL y la devuelve.
     *
     * @param serviciosId Identificador del servicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDTO} - El servicio buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la servicio.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDTO getServicio(@PathParam("serviciosId") Long serviciosId) throws WebApplicationException {
        return new ServicioDTO();
    }
 
    /**
     * Crea un nuevo servicio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param servicio {@link ServicioDTO} - El servicio que se desea
     * guardar.
     * @return JSON {@link ServicioDTO} - El servicio guardada con el atributo
     * id autogenerado.
     */
    @POST
    public ServicioDTO crearServicio(ServicioDTO servicio) throws WebApplicationException{
        return servicio;
    }
    
    /**
     * Modifica el servicio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico.
     *
     * @param serviciosId Identificador del servicio que se desea modificar.
     * @param servicio {@link ServicioDTO} - El servicio que se desea
     * modificar.
     * @return JSON {@link ServicioDTO} - La servicio modificada.
     */
    @PUT
    @Path("{serviciosId: \\d+}")
    public ServicioDTO modificarServicio(@PathParam("serviciosId") Long serviciosId, ServicioDTO nuevaServicio) throws WebApplicationException{
        return nuevaServicio;
    }
    
     /**
     * Borra el servicio con el id asociado recibido en la URL.
     *
     * @param serviciosId Identificador del servicio que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{serviciosId: \\d+}")
    public void deleteServicio(@PathParam("serviciosId") Long serviciosId) throws WebApplicationException {
        
    }
}
