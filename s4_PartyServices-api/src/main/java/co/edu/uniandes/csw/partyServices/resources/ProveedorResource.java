/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProveedorDTO;
import javax.ws.rs.GET;

/**
 *
 * @author estudiante
 */
    public class ProveedorResource 
{
    
     @GET
    public ProveedorDTO getProveedor(){
        return new ProveedorDTO();
    }
}
