/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author estudiante
 */
@Stateless
public class ProveedorLogic {

    private static final Logger LOGGER = Logger.getLogger(ProveedorLogic.class.getName());

    @Inject
    private ProveedorPersistence persistence;
    
    @Inject
    private AgendaLogic agendaLogic;

    public ProveedorEntity createProveedor(ProveedorEntity entity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de creación del proveedor");
        // Verifica la regla de negocio que dice que no puede haber dos proveedores con el mismo nombre
        if (persistence.findByName(entity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un proveedor con el nombre \"" + entity.getNombre() + "\"");
        }
        String contra = entity.getContrasenia();
        if (contra == null) {
            throw new BusinessLogicException("la contraseña no puede ser null");
        } else if (contra.length() < 8) {
            throw new BusinessLogicException("la contraseña debe tener más de 8 caracteres");
        } else if (validateNombre(entity.getContrasenia())) {
            throw new BusinessLogicException("la contraseña no debe contener caracteres especiales");
        } else if (contra.equals(entity.getNombre())) {
            throw new BusinessLogicException("la contraseña no puede ser igual al nombre de Usuario");
        }
        // Invoca la persistencia para crear el proveedor 
        persistence.create(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del proveedor");
        return entity;
    }

    private boolean validateNombre(String nombre) {
        Pattern pat = Pattern.compile("[a-zA-Z]");
        Matcher mat = pat.matcher(nombre);
        return (mat.matches() && !nombre.isEmpty());
    }

    /**
     *
     * Obtener todas los proveedores existentes en la base de datos.
     *
     * @return una lista de proveedores.
     */
    public List<ProveedorEntity> getProveedores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proveedores");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ProveedorEntity> proveedores = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de todos  los proveedores");
        return proveedores;
    }

    /**
     *
     * Obtener un proveedor por medio de su id.
     *
     * @param proveedorID: id del proveedor para ser buscada.
     * @return el proveedor solicitado por medio de su id.
     */
    public ProveedorEntity getProveedor(Long proveedorID) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proveedor con id = {0}", proveedorID);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ProveedorEntity proveedorEntity = persistence.find(proveedorID);
        if (proveedorEntity == null) {
            LOGGER.log(Level.SEVERE, "el proveedor con el id = {0} no existe", proveedorID);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proveedor con id = {0}", proveedorID);
        return proveedorEntity;
    }

        public ProveedorEntity validate(String nombre, String contrasenia) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proveedor con nombre = {0}", nombre);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ProveedorEntity proveedorEntity = persistence.validate(nombre, contrasenia);
        if (proveedorEntity == null) {
            LOGGER.log(Level.SEVERE, "el proveedor con el nombre = {0} no existe", nombre);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proveedor con nombre = {0}", nombre);
        return proveedorEntity;
    }
    /**
     *
     * Actualizar un proveedor.
     *
     * @param proveedorId: id del proveedor para buscarlo en la base de datos.
     * @param proveedorEntity: proveedor con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return el proveedor con los cambios actualizados en la base de datos.
     */
    public ProveedorEntity updateProveedor(Long proveedorId, ProveedorEntity proveedorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el proveedor con id = {0}", proveedorId);
        if (persistence.findByName(proveedorEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un proveedor con el nombre \"" + proveedorEntity.getNombre() + "\"");
        }

        String contra = proveedorEntity.getContrasenia();
        if (contra == null) {
            throw new BusinessLogicException("la contraseña no puede ser null");
        } else if (contra.length() < 8 || contra.length() > 35) {
            throw new BusinessLogicException("la contraseña debe tener más de 8 caracteres y máximo 35 caracteres");
        } else if (validateNombre(proveedorEntity.getNombre())) {
            throw new BusinessLogicException("la contraseña no debe contener caracteres especiales");
        } else if (contra.equals(proveedorEntity.getNombre())) {
            throw new BusinessLogicException("la contraseña no puede ser igual al nombre de Usuario");
        }
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ProveedorEntity newEntity = persistence.update(proveedorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el proveedor con id = {0}", proveedorEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un proveedor
     *
     * @param proveedorID: id del proveedor a borrar
     * @throws BusinessLogicException Si el proveedor a eliminar tiene .
     */
    public void deleteProveedor(Long proveedorID) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el proveedor con id = {0}", proveedorID);
        ProveedorEntity provEntity = persistence.find(proveedorID);
        LOGGER.log(Level.INFO, "Termina proceso de encontrar el proveedor con id = {0}", proveedorID);
        if (provEntity.getAgenda() != null) {
            if (!provEntity.getAgenda().getFechasOcupadas().isEmpty()) {
                throw new BusinessLogicException("No se puede borrar el proveedor pues tiene un evento pendiente");
            }
                    agendaLogic.deleteAgenda(provEntity.getAgenda().getId());

        }
        persistence.delete(proveedorID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el proveedor con id = {0}", proveedorID);
    }

}

