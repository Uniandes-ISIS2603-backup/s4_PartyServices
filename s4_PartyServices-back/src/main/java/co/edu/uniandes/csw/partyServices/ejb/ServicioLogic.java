/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ServicioPersistence;
import co.edu.uniandes.csw.partyServices.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Tomas Vargas
 */
public class ServicioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());

    @Inject
    private ServicioPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private TematicaPersistence tematicaPersistence;

    /**
     * Crea una servicio en la persistencia.
     *
     * @param servicioEntity La entidad que representa la servicio a
     * persistir.
     * @return La entiddad de la servicio luego de persistirla.
     * @throws BusinessLogicException Si la servicio a persistir ya existe.
     */
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la servicio");
        // Verifica la regla de negocio que dice que no puede haber dos servicioes con el mismo nombre
        if (persistence.findByTipo(servicioEntity.getTipo()) != null) {
            throw new BusinessLogicException("Ya existe una Servicio con el nombre \"" + servicioEntity.getTipo() + "\"");
        }
        // Invoca la persistencia para crear la servicio
        persistence.create(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la servicio");
        return servicioEntity;
    }

    /**
     *
     * Obtener todas las servicioes existentes en la base de datos.
     *
     * @return una lista de servicioes.
     */
    public List<ServicioEntity> getServicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las servicioes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ServicioEntity> servicios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las servicioes");
        return servicios;
    }

    /**
     *
     * Obtener una servicio por medio de su id.
     *
     * @param serviciosId: id de la servicio para ser buscada.
     * @return la servicio solicitada por medio de su id.
     */
    public ServicioEntity getServicio(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la servicio con id = {0}", serviciosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ServicioEntity servicioEntity = persistence.find(serviciosId);
        if (servicioEntity == null) {
            LOGGER.log(Level.SEVERE, "La servicio con el id = {0} no existe", serviciosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la servicio con id = {0}", serviciosId);
        return servicioEntity;
    }

    /**
     * Actualiza la información de una instancia de Servicio.
     *
     * @param serviciosId Identificador de la instancia a actualizar
     * @param servicioEntity Instancia de ServicioEntity con los nuevos datos.
     * @return Instancia de ServicioEntity con los datos actualizados.
     */
    public ServicioEntity updateServicio(Long serviciosId, ServicioEntity servicioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el libro con id = {0}", serviciosId);
        if (!validateTipo(servicioEntity.getTipo())) {
            throw new BusinessLogicException("El Tipo es inválido");
        }
        ServicioEntity newEntity = persistence.update(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el libro con id = {0}", servicioEntity.getId());
        return newEntity;
    }
    
    public boolean validateTipo(String pTipo) {
        return !(pTipo == null || pTipo.isEmpty());
    }

    /**
     * Borrar un servicio
     *
     * @param serviciosId: id del servicio a borrar
     * @throws BusinessLogicException Si la servicio a eliminar tiene servicios.
     */
    public void deleteServicio(Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio con id = {0}", serviciosId);
        
        List<ProveedorEntity> proveedores = getServicio(serviciosId).getProveedores();
        if (proveedores != null && !proveedores.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el servicio con id = " + serviciosId + " porque tiene proveedores asociados");
        }
        persistence.delete(serviciosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio con id = {0}", serviciosId);
    }
    
    public List<ServicioEntity> findAll()
     {
       return persistence.findAll() ;
     }
    
}
