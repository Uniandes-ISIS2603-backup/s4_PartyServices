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
 *Logica de agenda
 * @author Nicolas Hernandez
 */
public class AgendaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(AgendaLogic.class.getName());
    
    /**
     * Persistencia de la agenda
     */
    @Inject
    private AgendaPersistence agendaPersistence;
    
    /**
     * Persistencia de proveedor
     */
    @Inject 
    private ProveedorPersistence proveedorPersistence;
    
    /**
     * Crear una agenda. Crea una agenda y verifica que cumpla con las reglas de negocio
     * @param proveedorId id del proveedor de la agenda
     * @param agendaEntity la agenda que se va a agregar
     * @return la agenda agregada
     * @throws BusinessLogicException si no cumple la agenda con todas las reglas de negocio 
     */
    public AgendaEntity createAgenda(long proveedorId, AgendaEntity agendaEntity) throws BusinessLogicException
    {
        //verificacion que el proveedor de la agenda exista
        ProveedorEntity proveedor = proveedorPersistence.find(proveedorId);
        if(proveedor==null)
            throw new BusinessLogicException("No existe el proveedor de la agenda que desea ingresar");
        agendaEntity.setProveedor(proveedor);
        
        //Verificacion que no existan agendas con proveedores iguales
        for (ProveedorEntity proveedorEntity : proveedorPersistence.findAll()) {
            if(proveedorEntity!=null && proveedorEntity.getAgenda()!=null)
                throw new BusinessLogicException("Ya existe una agenda para el proveedor asociado");
        }
        
        
        //Verifica reglas negocio comunes con update
        verificarContenidoAgenda(agendaEntity);
        
        return agendaPersistence.create(agendaEntity);
    }
    
   /**
    * Obtener agenda. Busca una agenda en la base de datod
    * @param idAgenda el id de la agenda a buscar
    * @return la agenda buscada
    * @throws BusinessLogicException si se incumple alguna regla de negocio o no exixte la agenda 
    */
    public AgendaEntity getAgenda(long idAgenda) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Entrando a optener agenda {0}", idAgenda);
        AgendaEntity agenda= agendaPersistence.find(idAgenda);
        if(agenda==null)
            throw new BusinessLogicException("No existe agenda con id "+idAgenda);
        return agenda;
    }
    
    /**
     * Obtener agenda por proveedor
     * @param idProveedor id del proveedor
     * @return la agenda que pertenece a ese proveedor
     * @throws BusinessLogicException si se incumple alguna regla de negocio
     */
    public AgendaEntity getAgendaByProveedor(long idProveedor) throws BusinessLogicException
    {
        for (ProveedorEntity proveedorEntity : proveedorPersistence.findAll()) {
            if(proveedorEntity!=null && proveedorEntity.getAgenda()!=null && proveedorEntity.getAgenda().getId() == idProveedor)
                return proveedorEntity.getAgenda();
        }
        throw new BusinessLogicException("No existe una agenda para el proveedor");
        
    }
    
    /**
     * Actualizar una agenda. Actualiza una agenda
     * @param agendaEntity la agenda actualizada
     * @return la agenda actualizada
     * @throws BusinessLogicException si la nueva agenda incumple alguna regla de negocio 
     */
    public AgendaEntity updateAgenda(AgendaEntity agendaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Entrando a actualizar agenda {0}", agendaEntity.getId());
        
        //Verifica reglas de negocio en comun con crear
        verificarContenidoAgenda(agendaEntity);
        
        
        return agendaPersistence.update(agendaEntity);
        
    } 
    
    /**
     * Eliminar agenda
     * @param agendaId el id de la agenda a eliminar 
     */
    public void deleteAgenda(long agendaId)
    {
        agendaPersistence.delete(agendaId);
    }
    
    /**
     * Verifica que la agenda cumpla con las reglas de negocio
     * @param agendaEntity agenda a verificar
     * @throws BusinessLogicException si se incumple reglas de negocio
     */
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
                throw new BusinessLogicException("LUNES No cumple con las jornadas "+agendaEntity.getJornadaLunesND());          
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
