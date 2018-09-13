/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ServicioPersistence;
import co.edu.uniandes.csw.partyServices.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexión con la persistencia para la relación entre tematica y servicio
 * @author Tomas Vargas
 */

@Stateless
public class TematicaServiciosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(TematicaServiciosLogic.class.getName());

    @Inject
    private ServicioPersistence servicioPersistence;

    @Inject
    private TematicaPersistence tematicaPersistence;

    /**
     * Agregar un servicio a la tematica
     *
     * @param serviciosId El id servicio a guardar
     * @param tematicasId El id de la tematica en la cual se va a guardar el
     * servicio.
     * @return El servicio creado.
     */
    public ServicioEntity addServicio(Long serviciosId, Long tematicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un servicio a la tematica con id = {0}", tematicasId);
        TematicaEntity tematicaEntity = tematicaPersistence.find(tematicasId);
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        servicioEntity.setTematica(tematicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un servicio a la tematica con id = {0}", tematicasId);
        return servicioEntity;
    }

    /**
     * Retorna todos los servicios asociados a una tematica
     *
     * @param tematicasId El ID de la tematica buscada
     * @return La lista de servicios de la tematica
     */
    public List<ServicioEntity> getServicios(Long tematicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los servicios asociados a la tematica con id = {0}", tematicasId);
        return tematicaPersistence.find(tematicasId).getServicios();
    }

    /**
     * Retorna un servicio asociado a una tematica
     *
     * @param tematicasId El id de la tematica a buscar.
     * @param serviciosId El id del servicio a buscar
     * @return El servicio encontrado dentro de la tematica.
     * @throws BusinessLogicException Si el servicio no se encuentra en la
     * tematica
     */
    public ServicioEntity getServicio(Long tematicasId, Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} de la tematica con id = " + tematicasId, serviciosId);
        List<ServicioEntity> servicios = tematicaPersistence.find(tematicasId).getServicios();
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        int index = servicios.indexOf(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio con id = {0} de la tematica con id = " + tematicasId, serviciosId);
        if (index >= 0) {
            return servicios.get(index);
        }
        throw new BusinessLogicException("El servicio no está asociado a la tematica");
    }

    /**
     * Remplazar servicios de una tematica
     *
     * @param servicios Lista de servicios que serán los de la tematica.
     * @param tematicasId El id de la tematica que se quiere actualizar.
     * @return La lista de servicios actualizada.
     */
    public List<ServicioEntity> replaceServicios(Long tematicasId, List<ServicioEntity> servicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la tematica con id = {0}", tematicasId);
        TematicaEntity tematicaEntity = tematicaPersistence.find(tematicasId);
        List<ServicioEntity> servicioList = servicioPersistence.findAll();
        for (ServicioEntity servicio : servicioList) {
            if (servicios.contains(servicio)) {
                servicio.setTematica(tematicaEntity);
            } else if (servicio.getTematica() != null && servicio.getTematica().equals(tematicaEntity)) {
                servicio.setTematica(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la tematica con id = {0}", tematicasId);
        return servicios;
    }
    
}
