/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;


import co.edu.uniandes.csw.partyServices.dtos.ValoracionDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *Clase que implementa el recurso "Sugerencias"
 * @author Jesús Orlnado Cárcamo Posada
 */
@Path("valoraciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ValoracionResource {
    
    /**
     * Retorna la valoracion con el id asociado recibido en la URL.
     *
     * @param valoracionesId Identificador de la valoracion que se desea obtener.
     * Este debe ser una cadena de dígitos.
     */
    @GET
    @Path("{valoracionesId: \\d+}")
    public ValoracionDTO darValoracion(@PathParam("valoracionesId") Long valoracionesId){
        return new ValoracionDTO();
    }
    
    /**
     * Crea una nueva valoracion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param valoracion {@link ValoracionDTO} - La valoracion que se desea
     * guardar.
     * @return JSON {@link ValoracionDTO} - La valoracion guardada con el atributo
     * id autogenerado.
     */
    @POST
    public ValoracionDTO crearValoracion(ValoracionDTO valoracion){
        return valoracion;
    }
    
    /**
     * Modifica valoracion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico.
     *
     * @param valoracionesId Identificador de la valoracion que se desea modificar.
     * @param valoracion {@link ValoracionDTO} - La valoracion que se desea
     * modificar.
     * @return JSON {@link ValoracionDTO} - La valoracion modificada.
     */
    @PUT
    @Path("{valoracionesId: \\d+}")
    public ValoracionDTO modificarValoracion(@PathParam("valoracionesId") Long valoracionesId, ValoracionDTO valoracion){
        return valoracion;
    }
    
    /**
     * Borra la valoracion con el id asociado recibido en la URL.
     *
     * @param valoracionesId Identificador de la valoracion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{valoracionesId: \\d+}")
    public void deleteValoracion(@PathParam("valoracionesId") Long valoracionesId) {
        
    }
    
}
