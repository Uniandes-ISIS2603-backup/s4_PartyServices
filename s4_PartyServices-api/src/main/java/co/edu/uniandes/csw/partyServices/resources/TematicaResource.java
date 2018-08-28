/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.TematicaDTO;
import javax.ws.rs.GET;

/**
 *
 * @author estudiante
 */
public class TematicaResource {
    
    @GET
    public TematicaDTO consultarTematica()
{
    return new TematicaDTO();
}
    
}
