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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Logica de fecha
 * @author Nicolas Hernandez
 */
@Stateless
public class FechaLogic {
    
    private static final Logger LOGGER =Logger.getLogger(FechaLogic.class.getName());
    
    /**
     * Persistencia de fecha
     */
    @Inject
    private FechaPersistence fechaPersistence;
    
    /**
     * Persistencia de agenda
     */
    @Inject
    private AgendaPersistence agendaPersistence;
    
    /**
     * Crear una fecha
     * @param agendaId id de la agenda de la fecha
     * @param fechaEntity la fecha a crear
     * @return la fecha creada
     * @throws BusinessLogicException si la fecha no cumple con las reglas de negocio 
     */
    public FechaEntity createFecha(long agendaId, FechaEntity fechaEntity) throws BusinessLogicException
    {
        //Verificacion regla de negocio de las jornadas
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()) == null){
                throw new BusinessLogicException("No cumple con las jornadas posibles");          
        }
        if(ConstantesJornada.desdeValor( fechaEntity.getJornada() ).equals(ConstantesJornada.NINGUNA)){          
            throw new BusinessLogicException("La jornada ninguna no es valida");
        } 
        
        if(fechaEntity.getDia()==null)
            throw new BusinessLogicException("La fecha es nula");
            
        
        //Verificacion regla de negocio no se pueden crear fechas del pasado
        Date dia= new Date();
        if(dia.compareTo(fechaEntity.getDia())>=0)
            throw new BusinessLogicException("El dia de la fecha no puede ser menor o igual al dia actual");
        
        AgendaEntity agenda = agendaPersistence.find(agendaId);
        if(agenda==null)
            throw new BusinessLogicException("La agenda de la fecha que esta creando no existe");
        fechaEntity.setAgenda(agenda);
        
        Collection<FechaEntity>fechasOcupadas=agenda.getFechasOcupadas();
        if(fechasOcupadas==null)
            fechasOcupadas=new ArrayList<>();
        fechasOcupadas.add(fechaEntity);
        agenda.setFechasOcupadas(fechasOcupadas);
        agendaPersistence.update(agenda);
        return fechaPersistence.create(fechaEntity);
    }
    
    /**
     * Obtener una fecha por agenda, dia y jornada. Obtiene la fecha de una agenda de una jornada y dia especifico
     * @param dia dia de la fecha
     * @param idAgenda agenda de la fecha
     * @param jornada jornada de la fecha
     * @return la fecha que cumple con los criterios de busqueda
     * @throws BusinessLogicException si se incumple reglas de negocio o no existe la fecha
     */
    public FechaEntity getFechaPorDiaAgendaJornada(Date dia, long idAgenda, String jornada) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Entrando a optener fecha {0}", dia);
        FechaEntity fecha= fechaPersistence.findByDiaAgendaAndJornada(dia, idAgenda, jornada);
        if(fecha==null){
            LOGGER.log(Level.INFO,"No se encuentra fecha con el id {0}", dia);
            throw new BusinessLogicException("No existe fecha con dia, idAgenda y jornada "+dia.toString()+idAgenda+jornada);
        }
        return fecha;
    }
    
    /**
     * Obtener fecha por id. Obtiene la fecha por su id
     * @param idFecha id de la fecha
     * @return la fecha buscada
     * @throws BusinessLogicException si se incumple alguna regla de negocio o no existe la fecha 
     */
    public FechaEntity getFechaID(long idFecha) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Entrando a optener fecha {0}", idFecha);
        FechaEntity fecha= fechaPersistence.find(idFecha);
        if(fecha==null){
            LOGGER.log(Level.INFO,"No se encuentra fecha con el id {0}", idFecha);
            throw new BusinessLogicException("No existe fecha con id "+idFecha);
        }
        return fecha;
    }
    
    /**
     * Obtener fechas de una agenda. Obtiene las fechas de la agenda
     * @param idAgenda id de la agenda 
     * @return las fechas de la agenda
     * @throws BusinessLogicException si se incumple regla de negocio
     */
    public List<FechaEntity> getFechasDeAgenda(long idAgenda) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Entrando a optener fechas con agenda:  {0}", idAgenda);
        return  fechaPersistence.getFechasDeAgenda(idAgenda);
    }
    
    /**
     * Actualizar fecha. Actualiza una fecha
     * @param fechaEntity la fecha a actualizar
     * @return la fecha actualizada
     * @throws BusinessLogicException si la fecha no cumple con las reglas de negocio 
     */
    public FechaEntity updateFecha(FechaEntity fechaEntity) throws BusinessLogicException
    {
        //Verificacion regla de negocio de las jornadas
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()) == null){
                throw new BusinessLogicException("No cumple con las jornadas posibles al actualizar la fecha");          
        }
        if(ConstantesJornada.desdeValor(fechaEntity.getJornada()).darValor().equals(ConstantesJornada.NINGUNA.darValor())){
                throw new BusinessLogicException("No es valido a la jornada ninguna actualizando la fecha");          
        }
        return fechaPersistence.update(fechaEntity);
    }
    
    /**
     * Eliminar fecha. Elimina una fecha
     * @param fechaId el id de la fecha a eliminar
     * @throws BusinessLogicException si se incumple reglas de negocio
     */
    public void deleteFecha(long fechaId) throws BusinessLogicException
    {
        FechaEntity fecha =fechaPersistence.find(fechaId);
        if(fecha!=null && fecha.getEventos()!=null && !fecha.getEventos().isEmpty())
            throw new BusinessLogicException("No se puede eliminar la fecha porque tiene "+fecha.getEventos().size()+" eventos");
            
        fechaPersistence.delete(fechaId);
    }
    
}
