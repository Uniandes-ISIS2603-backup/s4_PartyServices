/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity.Jornada;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class AgendaLogic {
    
    @Inject
    private AgendaPersistence agendaPersistence;
    
    @Inject 
    private ProveedorPersistence proveedorPersistence;
    
    public AgendaEntity createAgenda(long proveedorId, AgendaEntity agendaEnitity) throws BusinessLogicException
    {
        
        
        //Verificacion que no existan agendas con proveedores iguales
        AgendaEntity agendaExistente = agendaPersistence.findByProveedor(proveedorId);
        if(agendaExistente!=null)
            throw new BusinessLogicException("Ya existe una agenda para el proveedor seleccionado");
        
        //Verificacion regla de negocio respecto al rango posible de la fecha de penitencia
        if(agendaEnitity.getFechaPenitencia()!=null)
        {
            if(agendaEnitity.getFechaPenitencia().compareTo(new Date())<0)
                throw new BusinessLogicException("La fecha de penitencia no puede ser menor al dia actual");
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.MONTH, 1);
            dia=cal.getTime();
            if(agendaEnitity.getFechaPenitencia().compareTo(dia)>0)
                throw new BusinessLogicException("La fecha de penitencia no puede ser mayor a un mes desde hoy");
        }
        
        //Verificacion regla del negocio del cumplimiento del formato para fehcas en que no labora el proveedor
        String fechasNoValidas=agendaEnitity.getFechasNoDisponibles();
        try {
            validarFormatoFechasNoLaborables(fechasNoValidas);
        } catch (Exception e) {
            throw new BusinessLogicException("No corresponde el formato de fechas en que no labora. "+e.getMessage());
        }
        
       
        
        
        ProveedorEntity proveedor= proveedorPersistence.find(proveedorId);
        agendaEnitity.setProveeedor(proveedor);
        return agendaPersistence.create(agendaEnitity);
    }
    
    public void validarFormatoFechasNoLaborables(String fechasNoValidas) throws Exception
    {
        JsonParser parser = new JsonParser();
        JsonObject jsonFechasNoValidas= (JsonObject) parser.parse(fechasNoValidas);
        for (AgendaEntity.DiaSemana value : AgendaEntity.DiaSemana.values()) {
            if(jsonFechasNoValidas.get(value.darValor())!=null)
            {
                String jornada= jsonFechasNoValidas.get(value.darValor()).getAsString();
                if(FechaEntity.Jornada.desdeValor(jornada)==null)
                    throw new BusinessLogicException("No cumple con las jornadas posibles");          
        
            }
            else
                throw new BusinessLogicException("No ingresa todos los dias de la semana, en caso de no laborar un dia poner valor de jornada como "+FechaEntity.Jornada.NINGUNA);
        }
    }   
}
