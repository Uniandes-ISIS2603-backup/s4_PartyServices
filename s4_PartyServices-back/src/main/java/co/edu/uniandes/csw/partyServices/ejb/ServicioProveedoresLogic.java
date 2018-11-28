/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ServicioPersistence;
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
public class ServicioProveedoresLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioProveedoresLogic.class.getName());

    @Inject
    private ServicioPersistence servicioPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ProveedorPersistence proveedorPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un proveedor a el servicio
     *
     * @param proveedoresId El id proveedor a guardar
     * @param serviciosId El id de el servicio en la cual se va a guardar el
     * proveedor.
     * @return El proveedor creado.
     */
    public ProveedorEntity addProveedor(Long proveedoresId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un proveedor a el servicio con id = {0}", serviciosId);
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        proveedorEntity.setServicio(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un proveedor a el servicio con id = {0}", serviciosId);
        return proveedorEntity;
    }

    /**
     * Retorna todos los proveedores asociados a una servicio
     *
     * @param serviciosId El ID de el servicio buscada
     * @return La lista de proveedores de el servicio
     */
    public List<ProveedorEntity> getProveedores(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los proveedores asociados a el servicio con id = {0}", serviciosId);
        return servicioPersistence.find(serviciosId).getProveedores();
    }

    /**
     * Retorna un proveedor asociado a una servicio
     *
     * @param serviciosId El id de el servicio a buscar.
     * @param proveedoresId El id del proveedor a buscar
     * @return El proveedor encontrado dentro de el servicio.
     * @throws BusinessLogicException Si el proveedor no se encuentra en la
     * servicio
     */
    public ProveedorEntity getProveedor(Long serviciosId, Long proveedoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proveedor con id = {0} de el servicio con id = " + serviciosId, proveedoresId);
        List<ProveedorEntity> proveedores = servicioPersistence.find(serviciosId).getProveedores();
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        int index = proveedores.indexOf(proveedorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proveedor con id = {0} de el servicio con id = " + serviciosId, proveedoresId);
        if (index >= 0) {
            return proveedores.get(index);
        }
        throw new BusinessLogicException("El proveedor no está asociado a el servicio");
    }

    /**
     * Remplazar proveedores de una servicio
     *
     * @param proveedores Lista de proveedores que serán los de el servicio.
     * @param serviciosId El id de el servicio que se quiere actualizar.
     * @return La lista de proveedores actualizada.
     */
    public List<ProveedorEntity> replaceProveedores(Long serviciosId, List<ProveedorEntity> proveedores) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio con id = {0}", serviciosId);
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        List<ProveedorEntity> proveedorList = proveedorPersistence.findAll();
        for (ProveedorEntity proveedor : proveedorList) {
            if (proveedores.contains(proveedor)) {
                proveedor.setServicio(servicioEntity);
            } else if (proveedor.getServicio() != null && proveedor.getServicio().equals(servicioEntity)) {
                proveedor.setServicio(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio con id = {0}", serviciosId);
        return proveedores;
    }
}
