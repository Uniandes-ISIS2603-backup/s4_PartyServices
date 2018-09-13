/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.SugerenciaDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.logging.Logger;

/**
 *Clase que implementa el recurso "Sugerencias"
 * @author Jesús Orlnado Cárcamo Posada
 */
@Path("sugerencias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SugerenciaResource {
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaResource.class.getName());
    
    /*@Inject
    private SugerencialLogic sugerenciaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    */
    
    /**
     * Retorna la sugerencia con el id asociado recibido en la URL.
     *
     * @param sugerenciasId Identificador de la sugerencia que se desea obtener.
     * @return JSON {@link SugerenciaDTO} - La editorial buscada
     * Este debe ser una cadena de dígitos.
     */
    @GET
    @Path("{sugerenciasId: \\d+}")
    public SugerenciaDTO darSugerencia(@PathParam("sugerenciasId") Long sugerenciasId){
        return new SugerenciaDTO();
    }
    
    /**
     * Crea una nueva sugerencia con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param sugerencia {@link SugerenciaDTO} - La sugerencia que se desea
     * guardar.
     * @return JSON {@link SugerenciaDTO} - La sugerencia guardada con el atributo
     * id autogenerado.
     */
    @POST
    public SugerenciaDTO crearSugerencia(SugerenciaDTO sugerencia){
        return sugerencia;
    }
    
    /**
     * Modifica sugerencia con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico.
     *
     * @param sugerenciasId Identificador de la sugerencia que se desea modificar.
     * @param sugerencia {@link SugerenciaDTO} - La sugerencia que se desea
     * modificar.
     * @return JSON {@link SugerenciaDTO} - La sugerencia modificada.
     */
    @PUT
    @Path("{sugerenciasId: \\d+}")
    public SugerenciaDTO modificarSugerencia(@PathParam("sugerenciasId") Long sugerenciasId, SugerenciaDTO sugerencia){
        return sugerencia;
    }
    
    /**
     * Borra la sugerencia con el id asociado recibido en la URL.
     *
     * @param sugerenciasId Identificador de la sugerencia que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{sugerenciasId: \\d+}")
    public void deleteEditorial(@PathParam("sugerenciasId") Long sugerenciasId) {
        
    }
    
}
