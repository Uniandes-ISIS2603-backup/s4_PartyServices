/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class FechaLogic {
    
    private static final Logger LOGGER =Logger.getLogger(FechaLogic.class.getName());
    
    @Inject
    private FechaPersistence fechaPersistence;
    
    @Inject
    private AgendaPersistence agendaPersistence;
    
    public FechaEntity createFecha(long agendaId, FechaEntity fechaEnitity) throws BusinessLogicException
    {
        //Verificacion regla de negocio de las jornadas
        if(FechaEntity.Jornada.desdeValor(fechaEnitity.getJornada())==null){
                throw new BusinessLogicException("No cumple con las jornadas posibles");          
        }
            
        //Verificacion regla de negocio deben existir eventos
        if(fechaEnitity.getEventos()==null)
            throw new BusinessLogicException("Debe tener eventos la fecha");
        if(fechaEnitity.getEventos().size()<=0)
            throw new BusinessLogicException("Debe tener eventos la fecha");
        
        //Verificacion regla de negocio no se pueden crear fechas del pasado
        Date dia= new Date();
        dia.setHours(0);
        dia.setMinutes(0);
        dia.setSeconds(0);
        if(dia.compareTo(fechaEnitity.getDia())>=0)
            throw new BusinessLogicException("El dia de la fecha no puede ser menor o igual al dia actual");
        
        AgendaEntity agenda = agendaPersistence.find(agendaId);
        fechaEnitity.setAgenda(agenda);
        return fechaPersistence.create(fechaEnitity);
    }
}
