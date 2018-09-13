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
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Stateless
public class ClienteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    @Inject
    private ClientePersistence persistence;
    
    
    public ClienteEntity createCliente(ClienteEntity clienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se crea el cliente");
        
        if(clienteEntity.getLogin().length()>8){
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 20000 caracteres");
        }
        if(clienteEntity.getContrasenia().length()>8){
            throw new BusinessLogicException("El tamaño contraseña no debe ser superior a los 8 caracteres");
        }
        
        persistence.create(clienteEntity);
        
        LOGGER.log(Level.INFO, "Se creó el cliente");
        return clienteEntity;
    }
    
    /**
     * Borrar una sugerencia.
     *
     * @param sugerenciaId: id de la sugerencia a borrar
     */
    public void deleteXCliente(Long sugerenciaId){
        LOGGER.log(Level.INFO, "Se quiere borrar el cliente", sugerenciaId);
        
        persistence.delete(sugerenciaId);
        
        LOGGER.log(Level.INFO, "Se borró el cliente", sugerenciaId);
    }
}
