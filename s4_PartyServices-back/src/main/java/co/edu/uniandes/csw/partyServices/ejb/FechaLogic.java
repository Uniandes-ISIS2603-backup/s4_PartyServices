/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class FechaLogic {
    
    @Inject
    private FechaPersistence fechaPersistence;
    
    @Inject
    private AgendaPersistence agendaPersistence;
    
    public FechaEntity createFecha(long agendaId, FechaEntity fechaEnitity)
    {
        //Verificacion regla de negocio de las jornadas
        
        
        AgendaEntity agenda = agendaPersistence.find(agendaId);
        fechaEnitity.setAgenda(agenda);
        return fechaPersistence.create(fechaEnitity);
    }
}
