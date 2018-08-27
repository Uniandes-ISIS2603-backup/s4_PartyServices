/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.SugerenciaDTO;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *Clase que implementa el recurso "Sugerencias"
 * @author Jesús Orlnado Cárcamo Posada
 */
@Path("sugerencias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SugerenciaResource {
    
    @GET
    public SugerenciaDTO darSugerencia(){
        return new SugerenciaDTO();
    }
    
}
