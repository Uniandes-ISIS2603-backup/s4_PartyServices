/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.partyServices.util.ConstantesJornada;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
        
        
        //Verificacion que no existan agendas con proveedores iguales
        AgendaEntity agendaExistente = agendaPersistence.findByProveedor(proveedorId);
        if(agendaExistente!=null)
            throw new BusinessLogicException("Ya existe una agenda para el proveedor seleccionado");
        
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
        
        //Verificacion regla del negocio del cumplimiento del formato para fehcas en que no labora el proveedor
        String fechasNoValidas=agendaEntity.getFechasNoDisponibles();
        try {
            validarFormatoFechasNoLaborables(fechasNoValidas);
        } catch (Exception e) {
            throw new BusinessLogicException("No corresponde el formato de fechas en que no labora. "+e.getMessage());
        }
        
       
        
        
        ProveedorEntity proveedor= proveedorPersistence.find(proveedorId);
        agendaEntity.setProveeedor(proveedor);
        return agendaPersistence.create(agendaEntity);
    }
    
    public void validarFormatoFechasNoLaborables(String fechasNoValidas) throws Exception
    {
        JsonParser parser = new JsonParser();
        JsonObject jsonFechasNoValidas= (JsonObject) parser.parse(fechasNoValidas);
        for (AgendaEntity.DiaSemana value : AgendaEntity.DiaSemana.values()) {
            if(jsonFechasNoValidas.get(value.darValor())!=null)
            {
                String jornada= jsonFechasNoValidas.get(value.darValor()).getAsString();
                if(ConstantesJornada.desdeValor(jornada)==null)
                    throw new BusinessLogicException("No cumple con las jornadas posibles");          
        
            }
            else
                throw new BusinessLogicException("No ingresa todos los dias de la semana, en caso de no laborar un dia poner valor de jornada como "+ConstantesJornada.NINGUNA);
        }
    }  
    public AgendaEntity getAgenda(long idAgenda)
    {
        LOGGER.log(Level.INFO,"Entrando a optener agenda ", idAgenda);
        AgendaEntity agenda= agendaPersistence.find(idAgenda);
        if(agenda==null)
            LOGGER.log(Level.INFO,"No se encuentra agenda con el id ", idAgenda);
        return agenda;
    }
    
    public AgendaEntity updateAgenda(AgendaEntity agendaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Entrando a actualizar agenda ", agendaEntity.getId());
        
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
        
        //Verificacion regla del negocio del cumplimiento del formato para fehcas en que no labora el proveedor
        String fechasNoValidas=agendaEntity.getFechasNoDisponibles();
        try {
            validarFormatoFechasNoLaborables(fechasNoValidas);
        } catch (Exception e) {
            throw new BusinessLogicException("No corresponde el formato de fechas en que no labora. "+e.getMessage());
        }
        
        
        AgendaEntity agenda= agendaPersistence.update(agendaEntity);
        if(agenda==null)
            LOGGER.log(Level.INFO,"No se encuentra agenda con el id ", agendaEntity.getId());
        return agenda;
    } 
    public void deleteAgenda(long agendaId)
    {
        agendaPersistence.delete(agendaId);
    }
}
