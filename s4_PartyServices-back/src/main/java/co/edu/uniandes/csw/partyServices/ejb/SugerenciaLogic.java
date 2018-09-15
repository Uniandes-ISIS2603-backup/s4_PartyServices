/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
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
public class SugerenciaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaLogic.class.getName());
    
    @Inject
    private SugerenciaPersistence persistence;
    
    /**
     * Crea una sugerencia en la persistencia. 
     * 
     * @param sugerenciaEntity La entidad que representa la sugerencia a persistir.
     * @return La entidad de la sugerencia luego de persistirla.
     * @throws BusinessLogicException si el tamaño de la sugerencia es mayor a los 20000 caracteres.
     */
    public SugerenciaEntity createSugerencia(SugerenciaEntity sugerenciaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la sugerencia");
        
        if(sugerenciaEntity.getComentario().length()>20000){
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 20000 caracteres");
        }
        
        persistence.create(sugerenciaEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación de la sugerencia");
        return sugerenciaEntity;
    }
    
    /**
     * Borrar una sugerencia.
     *
     * @param sugerenciaId: id de la sugerencia a borrar
     */
    public void deleteSugerencia(Long sugerenciaId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", sugerenciaId);
        
        persistence.delete(sugerenciaId);
        
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", sugerenciaId);
    }
}
