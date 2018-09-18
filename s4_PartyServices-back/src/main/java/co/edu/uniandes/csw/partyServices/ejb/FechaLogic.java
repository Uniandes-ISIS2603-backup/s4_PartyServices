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
import co.edu.uniandes.csw.partyServices.util.ConstantesJornada;
import java.util.Date;
import java.util.logging.Level;
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
    
    public FechaEntity createFecha(long agendaId, FechaEntity fechaEntity) throws BusinessLogicException
    {
        //Verificacion regla de negocio de las jornadas
        ConstantesJornada c = ConstantesJornada.desdeValor(fechaEntity.getJornada());
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()) == null){
                throw new BusinessLogicException("No cumple con las jornadas posibles");          
        }
        if(ConstantesJornada.desdeValor( fechaEntity.getJornada() ).equals(ConstantesJornada.NINGUNA)){          
            throw new BusinessLogicException("No cumple con las jornadas posibles");
        } 
            
        //Verificacion regla de negocio deben existir eventos
        if(fechaEntity.getEventos()==null)
            throw new BusinessLogicException("Debe tener eventos la fecha");
        if(fechaEntity.getEventos().size()<=0)
            throw new BusinessLogicException("Debe tener eventos la fecha");
        
        //Verificacion regla de negocio no se pueden crear fechas del pasado
        Date dia= new Date();
        dia.setHours(0);
        dia.setMinutes(0);
        dia.setSeconds(0);
        if(dia.compareTo(fechaEntity.getDia())>=0)
            throw new BusinessLogicException("El dia de la fecha no puede ser menor o igual al dia actual");
        
        AgendaEntity agenda = agendaPersistence.find(agendaId);
        if(agenda==null)
            throw new BusinessLogicException("La agenda de la fecha que esta creando no existe");
        fechaEntity.setAgenda(agenda);
        return fechaPersistence.create(fechaEntity);
    }
    
    public FechaEntity getFechaPorDia(Date dia)
    {
        LOGGER.log(Level.INFO,"Entrando a optener agenda ", dia);
        FechaEntity fecha= fechaPersistence.findByDia(dia);
        if(fecha==null)
            LOGGER.log(Level.INFO,"No se encuentra agenda con el id ", dia);
        return fecha;
    }
    
    public FechaEntity getFechaID(long idFecha)
    {
        LOGGER.log(Level.INFO,"Entrando a optener fecha ", idFecha);
        FechaEntity fecha= fechaPersistence.find(idFecha);
        if(fecha==null)
            LOGGER.log(Level.INFO,"No se encuentra fecha con el id ", idFecha);
        return fecha;
    }
    
    public FechaEntity updateFecha(FechaEntity fechaEntity) throws BusinessLogicException
    {
        //Verificacion regla de negocio de las jornadas
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()) == null){
                throw new BusinessLogicException("No cumple con las jornadas posibles");          
        }
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()).darValor().equals(ConstantesJornada.NINGUNA)){
                throw new BusinessLogicException("No cumple con las jornadas posibles");          
        }
        return fechaPersistence.update(fechaEntity);
    }
    
    public void deleteFecha(long fechaId) throws BusinessLogicException
    {
        FechaEntity fecha =fechaPersistence.find(fechaId);
        if(fecha!=null)
            if(fecha.getEventos()!=null)
                if(!fecha.getEventos().isEmpty())
                    throw new BusinessLogicException("No se puede eliminar la fecha porque tiene "+fecha.getEventos().size()+" eventos");
            
        fechaPersistence.delete(fechaId);
    }
    
}
