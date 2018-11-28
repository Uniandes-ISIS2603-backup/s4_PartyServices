/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.NotificacionPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class NotificacionLogic {

    private static final Logger LOGGER = Logger.getLogger(NotificacionLogic.class.getName());
    @Inject
    private NotificacionPersistence persistence;

    public NotificacionEntity createNotificacion(NotificacionEntity entity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de creación de la notificacion");
        if (entity.getMensaje() == null) {
            throw new BusinessLogicException("El mensaje no puede ser nulo");
        }
        if (entity.getMensaje().length() > 2000) {
            throw new BusinessLogicException("El mensaje es demasiado largo. Debe tener menos de 2000 caracteres");
        }
        if (entity.getTipoDeAviso() == null) {
            throw new BusinessLogicException("El mensaje no puede ser tipo Null");
        }
        boolean notifCliente = false;
        boolean notifProveedor = false;
        if (entity.getCliente() != null) {
            notifCliente = true;
        }
        if (entity.getProveedor() != null) {
            notifProveedor = true;
        }
        if (notifCliente && notifProveedor == false) {
            throw new BusinessLogicException("El mensaje debe tener un destinatario");
        }

        // Invoca la persistencia para crear la notificacion 
        persistence.create(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la notificacion");
        return entity;
    }

    /**
     *
     * Obtener todas los proveedores existentes en la base de datos.
     *
     * @return una lista de proveedores.
     */
    public List<NotificacionEntity> getNotificacion() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las notificaciones");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<NotificacionEntity> notificaciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de todos consulta de todas las notificaciones");
        return notificaciones;
    }

    /**
     *
     * Obtener una notificacion por medio de su id.
     *
     * @param notifID: id de la notidicacion para ser buscada.
     * @return la notificacion solicitada por medio de su id.
     */
    public NotificacionEntity getNotificacion(Long notifID) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la notificación  con id = {0}", notifID);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        NotificacionEntity notificacionEntity = persistence.find(notifID);
        if (notificacionEntity == null) {
            LOGGER.log(Level.SEVERE, "la notificacion con el id = {0} no existe", notifID);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la notificacion con id = {0}", notifID);
        return notificacionEntity;
    }

    /**
     *
     * Actualizar una notificacion.
     *
     * @param notifID: id de la notificacion para buscarlo en la base de datos.
     * @param entity: notificacion con los cambios para ser
     * actualizada, por ejemplo el mensaje.
     * @return la notificacion con los cambios actualizados en la base de datos.
     */
    public NotificacionEntity updateNotificacion(Long notifID, NotificacionEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la notificacion con id = {0}", notifID);
        if (entity.getMensaje() == null) {
            throw new BusinessLogicException("El mensaje no puede ser nulo");
        }
        if (entity.getMensaje().length() > 2000) {
            throw new BusinessLogicException("El mensaje es demasiado largo. Debe tener menos de 2000 caracteres");
        }
        if (entity.getTipoDeAviso() == null) {
            throw new BusinessLogicException("El mensaje no puede ser tipo Null");
        }
        boolean notifCliente = false;
        boolean notifProveedor = false;
        if (entity.getCliente() != null) {
            notifCliente = true;
        }
        if (entity.getProveedor() != null) {
            notifProveedor = true;
        }
        if (notifCliente && notifProveedor == false) {
            throw new BusinessLogicException("El mensaje debe tener un destinatario");
        }
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        NotificacionEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la notificacion con id = {0}", entity.getId());
        return newEntity;
    }

    /**
     * Borrar una notificacion
     *
     * @param notifID: id de la notificacion a borrar
     */
    public void deleteNotificacion(Long notifID) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la notificacion con id = {0}", notifID);

        persistence.delete(notifID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la notificacion con id = {0}", notifID);
    }

}
