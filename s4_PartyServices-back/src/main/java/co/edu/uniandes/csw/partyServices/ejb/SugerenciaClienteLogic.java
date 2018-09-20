/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 **
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Sugerencia y Cliente.
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Stateless
public class SugerenciaClienteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaClienteLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private SugerenciaPersistence sugerenciaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
     /**
     * Agregar un cliente a una sugerencia.
     *
     * @param sugerenciasId. El ID de la sugerencia a guardar
     * @param tematicasId. El ID de la temática de la cual hace parte la sugerencia.
     * @param clientesId. El ID del cliente al cual se le va a guardar la sugerencia.
     * @return El cliente agregado a la sugerencia. 
     */
    public ClienteEntity addCliente(Long tematicasId, Long sugerenciasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el cliente con id = {0} a la sugerencia con id = {1} que pertenece a la temática con id = {2}", new Object[]{clientesId, sugerenciasId, tematicasId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find( tematicasId, sugerenciasId);
        sugerenciaEntity.setCliente(clienteEntity);
        SugerenciaEntity actualizado = sugerenciaPersistence.update(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el cliente con id = {0} a la sugerencia con id = {1} que pertenece a la temática con id = {2}", new Object[]{clientesId, sugerenciasId, tematicasId});
        return actualizado.getCliente();
    }
    
    /**
     *
     * Obtener el cliente asociado a una sugerenica por medio del ID de esta y el ID de la temática a la que pertenece.
     *
     * @param sugerenciasId. ID de la sugerencia a ser buscada.
     * @param tematicasId. El ID de la temática de la cual hace parte la sugerencia.
     * @return el cliente de la sugerencia solicitada por medio del ID de esta y el ID de su temática.
     */
    public ClienteEntity getCliente(Long tematicasId, Long sugerenciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente de la sugerencia con id = {0} que pertenece a la temática con id " + tematicasId, sugerenciasId);
        ClienteEntity clienteEntity = sugerenciaPersistence.find(tematicasId, sugerenciasId).getCliente();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente de la sugerencia con id = {0} que pertenece a la temática con id " + tematicasId, sugerenciasId);
        return clienteEntity;
    }
    
    /**
     * Remplazar el cliente de una sugerencia.
     *
     * @param sugerenciasId. El ID de la sugerencia que se quiere actualizar.
     * @param tematicasId. El ID de la temática de la cual hace parte la sugerencia.
     * @param clientesId. El ID del nuevo cliente asociado a la sugerencia.
     * @return el nuevo autor asociado.
     */
    public ClienteEntity replaceCliente(Long tematicasId, Long sugerenciasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el cliente de la sugerencia con ID = {0}, por el cliente con ID = {1}, que pertenece a la temática con ID = {2}", new Object[]{sugerenciasId, clientesId, tematicasId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId, sugerenciasId);
        sugerenciaEntity.setCliente(clienteEntity);
        SugerenciaEntity actualizado = sugerenciaPersistence.update(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar el cliente de la sugerencia con ID = {0}, por el cliente con ID = {1}, que pertenece a la temática con ID = {2}", new Object[]{sugerenciasId, clientesId, tematicasId});
        return actualizado.getCliente();
    }
    
    /**
     * Borrar el cliente de una sugerencia.
     *
     * @param sugerenciasId. La sugerencia a la que se desea borrar el cliente.
     * @param tematicasId. El ID de la temática de la cual hace parte la sugerencia.
     * @throws BusinessLogicException si la sugerencia no tiene cliente.
     */
    public void removeCliente(Long tematicasId, Long sugerenciasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente de la sugerencia con ID = {0}, que pertenece a la temática con ID = " + tematicasId, sugerenciasId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId, sugerenciasId);
        if (sugerenciaEntity.getCliente() == null) {
            throw new BusinessLogicException("La sugerencia no tiene cliente");
        }
        ClienteEntity clienteEntity = clientePersistence.find(sugerenciaEntity.getCliente().getId());
        sugerenciaEntity.setCliente(null);
        sugerenciaPersistence.update(sugerenciaEntity);
        clienteEntity.getSugerencias().remove(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0} de la sugerencia con id = " + sugerenciasId, clienteEntity.getId());
    }
}
