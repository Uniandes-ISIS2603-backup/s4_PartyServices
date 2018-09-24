/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 **
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Valoracion y Cliente.
 *
 * @author ELIAS NEGRETE
 */
@Stateless
public class ValoracionClienteLogic {

    private static final Logger LOGGER = Logger.getLogger(ValoracionClienteLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ValoracionPersistence valoracionPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un cliente a una valoracion.
     *
     * @param valoracionesId. El ID de la valoracion a guardar
     * @param proveedorId. El ID del proveedor de la cual hace parte la
     * valoracion.
     * @param clientesId. El ID del cliente al cual se le va a guardar la
     * valoracion.
     * @return El cliente agregado a la valoracion.
     */
    public ClienteEntity addCliente(Long proveedorId, Long valoracionesId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el cliente con id = {0} a la valoracion con id = {1} que pertenece al proveedor con id = {2}", new Object[]{clientesId, valoracionesId, proveedorId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        ValoracionEntity valEntity = valoracionPersistence.find(proveedorId, valoracionesId);
        valEntity.setCliente(clienteEntity);
        ValoracionEntity actualizado = valoracionPersistence.update(valEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el cliente con id = {0} a la valoracion con id = {1} que pertenece al proveedor con id = {2}", new Object[]{clientesId, valoracionesId, proveedorId});
        return actualizado.getCliente();
    }

    /**
     *
     * Obtener el cliente asociado a una valoracion por medio del ID de esta y
     * el ID del proveedor al que pertenece.
     *
     * @param valoracionesId. ID de la valoracion a ser buscada.
     * @param proveedorId. El ID del proveedor anotado por la valoración.
     * @return el cliente de la sugerencia solicitada por medio del ID de esta y
     * el ID de su proveedor.
     */
    public ClienteEntity getCliente(Long proveedorId, Long valoracionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente de la valoracion con id = {0} que pertenece al proveedor con id " + proveedorId, valoracionesId);
        ClienteEntity clienteEntity = valoracionPersistence.find(proveedorId, valoracionesId).getCliente();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente de la valoracion con id = {0} que pertenece al proveedor con id " + proveedorId, valoracionesId);
        return clienteEntity;
    }

    /**
     * Remplazar el cliente de una valoracion.
     *
     * @param valoracionesId. El ID de la valoración que se quiere actualizar.
     * @param proveedorId. El ID del proveedir al que se le hace la valoracion.
     * @param clientesId. El ID del nuevo cliente asociado a la valoración.
     * @return el nuevo cliente asociado.
     */
    public ClienteEntity replaceCliente(Long proveedorId, Long valoracionesId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el cliente de la valoracion con ID = {0}, por el cliente con ID = {1}, que pertenece al proveedor con ID = {2}", new Object[]{valoracionesId, clientesId, proveedorId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        ValoracionEntity valoracionEntity = valoracionPersistence.find(proveedorId, valoracionesId);
        valoracionEntity.setCliente(clienteEntity);
        ValoracionEntity actualizado = valoracionPersistence.update(valoracionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar el cliente de la valoracion con ID = {0}, por el cliente con ID = {1}, que pertenece a la proveedor con ID = {2}", new Object[]{valoracionesId, clientesId, proveedorId});
        return actualizado.getCliente();
    }

    /**
     * Borrar el cliente de una valoracion.
     *
     * @param valoracionId. La valoracion a la que se desea borrar el cliente.
     * @param proveedorId. El ID del proveedor calificado.
     * @throws BusinessLogicException si la valoracion no tiene cliente.
     */
    public void removeCliente(Long proveedorId, Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente de la valoracion con ID = {0}, que pertenece a la proveedor con ID = " + proveedorId, valoracionId);
        ValoracionEntity valEntity = valoracionPersistence.find(proveedorId, valoracionId);
        if (valEntity.getCliente() == null) {
            throw new BusinessLogicException("La valoracion no tiene cliente");
        }
        ClienteEntity clienteEntity = clientePersistence.find(valEntity.getCliente().getId());
        valEntity.setCliente(null);
        valoracionPersistence.update(valEntity);
        clienteEntity.getValoraciones().remove(valEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0} de la valoracion con id = " + valoracionId, clienteEntity.getId());
    }
}
