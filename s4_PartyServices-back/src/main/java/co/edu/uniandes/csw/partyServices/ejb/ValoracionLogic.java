/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Stateless
public class ValoracionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ValoracionLogic.class.getName());
    
    @Inject
    private ValoracionPersistence persistence;
    
    /**
     * Crea una valoración en la persistencia. 
     * 
     * @param valoracionEntity La entidad que representa la valoracion a persistir.
     * @return La entidad d ela valoración luego de persistirla.
     * @throws BusinessLogicException si el tamaño de la valoración es mayor a los 20000 caracteres.
     */
    public ValoracionEntity createValoracion(ValoracionEntity valoracionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la valoracion");
        if (valoracionEntity != null) {
            if ( valoracionEntity.getComentario() != null && valoracionEntity.getComentario().length() > 20000) {
                throw new BusinessLogicException("El tamaño del texto es superior a los 20000 caracteres");
            }
        }
        
        persistence.create(valoracionEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación de la valoracion");
        return valoracionEntity;
    }
    
    /**
     * Borrar una valoracion
     *
     * @param valoracionId: id de la valoracion a borrar
     */
    public void deleteValoracion(Long valoracionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", valoracionId);
        
        persistence.delete(valoracionId);
        
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", valoracionId);
    }
    
}
