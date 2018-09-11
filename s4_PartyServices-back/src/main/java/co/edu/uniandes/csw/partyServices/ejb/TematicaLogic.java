/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Tomas Vargas
 */
@Stateless
public class TematicaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(TematicaLogic.class.getName());

    @Inject
    private TematicaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una tematica en la persistencia.
     *
     * @param tematicaEntity La entidad que representa la tematica a
     * persistir.
     * @return La entiddad de la tematica luego de persistirla.
     * @throws BusinessLogicException Si la tematica a persistir ya existe.
     */
    public TematicaEntity createTematica(TematicaEntity tematicaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la tematica");
        // Verifica la regla de negocio que dice que no puede haber dos tematicaes con el mismo nombre
        if (persistence.findByName(tematicaEntity.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Tematica con el nombre \"" + tematicaEntity.getName() + "\"");
        }
        // Invoca la persistencia para crear la tematica
        persistence.create(tematicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la tematica");
        return tematicaEntity;
    }

    /**
     *
     * Obtener todas las tematicaes existentes en la base de datos.
     *
     * @return una lista de tematicaes.
     */
    public List<TematicaEntity> getTematicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las tematicaes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<TematicaEntity> tematicas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las tematicaes");
        return tematicas;
    }

    /**
     *
     * Obtener una tematica por medio de su id.
     *
     * @param tematicasId: id de la tematica para ser buscada.
     * @return la tematica solicitada por medio de su id.
     */
    public TematicaEntity getTematica(Long tematicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la tematica con id = {0}", tematicasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        TematicaEntity tematicaEntity = persistence.find(tematicasId);
        if (tematicaEntity == null) {
            LOGGER.log(Level.SEVERE, "La tematica con el id = {0} no existe", tematicasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la tematica con id = {0}", tematicasId);
        return tematicaEntity;
    }

    /**
     *
     * Actualizar una tematica.
     *
     * @param tematicasId: id de la tematica para buscarla en la base de
     * datos.
     * @param tematicaEntity: tematica con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la tematica con los cambios actualizados en la base de datos.
     */
    public TematicaEntity updateTematica(Long tematicasId, TematicaEntity tematicaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la tematica con id = {0}", tematicasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        TematicaEntity newEntity = persistence.update(tematicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la tematica con id = {0}", tematicaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un tematica
     *
     * @param tematicasId: id de la tematica a borrar
     * @throws BusinessLogicException Si la tematica a eliminar tiene servicios.
     */
    public void deleteTematica(Long tematicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la tematica con id = {0}", tematicasId);
        
        List<ServicioEntity> servicios = getTematica(tematicasId).getServicios();
        if (servicios != null && !servicios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la tematica con id = " + tematicasId + " porque tiene servicios asociados");
        }
        
        List<SugerenciaEntity> sugerencias = getTematica(tematicasId).getSugerencias();
        if (sugerencias != null && !sugerencias.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la tematica con id = " + tematicasId + " porque tiene sugerencias asociadas");
        }
        persistence.delete(tematicasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la tematica con id = {0}", tematicasId);
    }
    
}
