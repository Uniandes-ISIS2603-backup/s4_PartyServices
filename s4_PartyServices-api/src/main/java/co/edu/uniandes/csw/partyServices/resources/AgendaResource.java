/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.AgendaDTO;

/**
 *
 * @author n.hernandezs
 */
public class AgendaResource {
    
    @GET 
    public AgendaDTO obtenerAgenda()
    {
        return new AgendaDTO();
    }
}
