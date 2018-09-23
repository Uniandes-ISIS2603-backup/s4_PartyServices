/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.partyServices.util.ConstantesJornada;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class AgendaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(AgendaLogic.class.getName());
    
    @Inject
    private AgendaPersistence agendaPersistence;
    
    @Inject 
    private ProveedorPersistence proveedorPersistence;
    
    public AgendaEntity createAgenda(long proveedorId, AgendaEntity agendaEntity) throws BusinessLogicException
    {
        //verificacion que el proveedor de la agenda exista
        ProveedorEntity proveedor = proveedorPersistence.find(proveedorId);
        if(proveedor==null)
            throw new BusinessLogicException("No existe el proveedor de la agenda que desea ingresar");
        agendaEntity.setProveeedor(proveedor);
        
        //Verificacion que no existan agendas con proveedores iguales
        AgendaEntity agendaExistente = agendaPersistence.findByProveedor(proveedorId);
        if(agendaExistente!=null)
            throw new BusinessLogicException("Ya existe una agenda para el proveedor seleccionado");
        
        //Verifica reglas negocio comunes con update
        verificarContenidoAgenda(agendaEntity);
        
        return agendaPersistence.create(agendaEntity);
    }
    
   
    public AgendaEntity getAgenda(long idAgenda)
    {
        LOGGER.log(Level.INFO,"Entrando a optener agenda ", idAgenda);
        AgendaEntity agenda= agendaPersistence.find(idAgenda);
        
        return agenda;
    }
    public AgendaEntity getAgendaByProveedor(long idProveedor)
    {
        LOGGER.log(Level.INFO,"Entrando a optener agenda ", idProveedor);
        AgendaEntity agenda= agendaPersistence.findByProveedor(idProveedor);
        
        return agenda;
    }
    
    public AgendaEntity updateAgenda(AgendaEntity agendaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Entrando a actualizar agenda ", agendaEntity.getId());
        
        //Verifica reglas de negocio en comun con crear
        verificarContenidoAgenda(agendaEntity);
        
        
        AgendaEntity agenda= agendaPersistence.update(agendaEntity);
        
        return agenda;
    } 
    public void deleteAgenda(long agendaId)
    {
        agendaPersistence.delete(agendaId);
    }
    
    private void verificarContenidoAgenda(AgendaEntity agendaEntity) throws BusinessLogicException{
        //Verificacion regla de negocio respecto al rango posible de la fecha de penitencia
        if(agendaEntity.getFechaPenitencia()!=null)
        {
            if(agendaEntity.getFechaPenitencia().compareTo(new Date())<0)
                throw new BusinessLogicException("La fecha de penitencia no puede ser menor al dia actual");
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.MONTH, 1);
            dia=cal.getTime();
            if(agendaEntity.getFechaPenitencia().compareTo(dia)>0)
                throw new BusinessLogicException("La fecha de penitencia no puede ser mayor a un mes desde hoy");
        }
        
        //Verificacion regla de negocio de las jornadas de las fechas que no labora el proveedor
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaLunesND()) == null){
                throw new BusinessLogicException("LUNES No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaMartesND()) == null){
                throw new BusinessLogicException("MARTES No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaMiercolesND()) == null){
                throw new BusinessLogicException("MIERCOLES No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaJuevesND()) == null){
                throw new BusinessLogicException("JUEVES No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaViernesND()) == null){
                throw new BusinessLogicException("VIERNES No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaSabadoND()) == null){
                throw new BusinessLogicException("SABADO No cumple con las jornadas ");          
        }
        if(ConstantesJornada.desdeValor(agendaEntity.getJornadaDomingoND()) == null){
                throw new BusinessLogicException("DOMINGO No cumple con las jornadas ");          
        }
    }
}
