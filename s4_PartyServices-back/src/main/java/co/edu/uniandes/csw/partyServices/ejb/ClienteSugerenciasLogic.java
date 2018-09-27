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
import java.util.List;
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
public class ClienteSugerenciasLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteSugerenciasLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private SugerenciaPersistence sugerenciaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar una sugerencia a un cliente.
     *
     * @param sugerenciasId. El ID de la sugerencia a guardar
     * @param tematicasId. El ID de la temática de la cual hace parte la
     * sugerencia.
     * @param clientesId. El ID del cliente al cual se le va a guardar la
     * sugerencia.
     * @return La sugerencia agregada al cliente.
     */
    public SugerenciaEntity addSugerencia(Long tematicasId, Long sugerenciasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar la sugerencia con id = {1}, que pertenece a la temática con id = {2}, al cliente con id = {0}", new Object[]{clientesId, sugerenciasId, tematicasId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId, sugerenciasId);
        sugerenciaEntity.setCliente(clienteEntity);
        sugerenciaEntity.setNombreUsuario(clienteEntity.getLogin());
        SugerenciaEntity actualizado = sugerenciaPersistence.update(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar la sugerencia con id = {1}, que pertenece a la temática con id = {2}, al cliente con id = {0}", new Object[]{clientesId, sugerenciasId, tematicasId});
        return actualizado;
    }

    /**
     * Retorna todas las sugerencias asociadas a un cliente.
     *
     * @param clientesId El ID del cliente buscado
     * @return La lista de sugerencias del cliente.
     */
    public List<SugerenciaEntity> getSugerencias(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sugerencias asociadas al cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getSugerencias();
    }

    /**
     *
     * Obtener una sugerencia asociada a un cliente por medio del ID de esta y
     * el ID de la temática a la que pertenece.
     *
     * @param clientesId. El id del cliente a buscar.
     * @param sugerenciasId. ID de la sugerencia a buscar.
     * @param tematicasId. El ID de la temática de la cual hace parte la
     * sugerencia.
     * @return La sugerencia del cliente solicitada.
     * @throws BusinessLogicException Si la sugerencia no se encuentra en el
     * cliente.
     */
    public SugerenciaEntity getSugerencia(Long tematicasId, Long sugerenciasId, Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la sugerencia con id = {1}, que pertenece a la temática con id = {2}, del cliente con id = {0}", new Object[]{clientesId, sugerenciasId, tematicasId});
        List<SugerenciaEntity> sugerencias = clientePersistence.find(clientesId).getSugerencias();
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId, sugerenciasId);
        int index = sugerencias.indexOf(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la sugerencia con id = {1}, que pertenece a la temática con id = {2}, del cliente con id = {0}", new Object[]{clientesId, sugerenciasId, tematicasId});
        if (index >= 0) {
            return sugerencias.get(index);
        }
        throw new BusinessLogicException("La sugerencia no está asociada al cliente");
    }

    /**
     * Remueve la relación entre un cliente y sus sugerencias.
     *
     * @param clientesId. El ID del cliente que tiene las sugerencias a las que se quiere desasociar. 
     */
    public void removeSugerencias(Long clientesId){
        LOGGER.log(Level.INFO, "Inicia proceso de remover la relación de las sugerencia con el cliente cliente con id = {0}", clientesId);

        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        List<SugerenciaEntity> sugerenciaList = sugerenciaPersistence.findAll();
        for (SugerenciaEntity sugerencia : sugerenciaList) {
            if (sugerencia.getCliente() != null && sugerencia.getCliente().equals(clienteEntity)) {
                sugerencia.setCliente(null);
                sugerencia.setNombreUsuario("Anónimo");
                sugerenciaPersistence.update(sugerencia);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de remover la relación de las sugerencia con el cliente cliente con id = {0}", clientesId);
    }
}
