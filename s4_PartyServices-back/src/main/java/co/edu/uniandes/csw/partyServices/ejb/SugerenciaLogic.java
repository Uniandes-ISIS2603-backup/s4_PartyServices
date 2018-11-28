/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.TematicaPersistence;
import java.util.List;
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

    @Inject
    private TematicaPersistence tematicaPersistence;
    
    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Crea una sugerencia en la base de datos.
     *
     * @param tematicasId. ID de la temática la cual será madre de la nueva
     * sugerenica.
     * @param sugerenciaEntity La entidad que representa la sugerencia a
     * persistir.
     * @return La entidad de la sugerencia luego de persistirla, con un ID
     * asignado.
     * @throws BusinessLogicException si el tamaño de la sugerencia es mayor a
     * los 20000 caracteres.
     */
    public SugerenciaEntity createSugerencia(Long tematicasId, SugerenciaEntity sugerenciaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la sugerencia");

        if ((sugerenciaEntity.getTitulo() != null && sugerenciaEntity.getTitulo().length() > 50)||(sugerenciaEntity.getTitulo() == null)||(sugerenciaEntity.getTitulo().equals("")) ) {
            throw new BusinessLogicException("El tamaño del titulo no debe ser superior a los 50 caracteres o vacío");
        }
        if ((sugerenciaEntity.getComentario() != null && sugerenciaEntity.getComentario().length() > 1000) ||(sugerenciaEntity.getComentario()== null)||(sugerenciaEntity.getComentario().equals(""))) {
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 1000 caracteres o vacío");
        }
        if((!sugerenciaEntity.getLink().startsWith("https://"))||(!sugerenciaEntity.getVideo().startsWith("https://"))){
          throw new BusinessLogicException("El link de la imagen o video debe empezar en por https://");
        }
        
        TematicaEntity tematica = tematicaPersistence.find(tematicasId);
        sugerenciaEntity.setTematica(tematica);
        
        if (sugerenciaEntity.getCliente() != null) {
            ClienteEntity cliente = clientePersistence.find(sugerenciaEntity.getCliente().getId());
            sugerenciaEntity.setCliente(cliente);
        }

        LOGGER.log(Level.INFO, "Termina proceso de creación de la sugerencia");

        persistence.create(sugerenciaEntity);
        

        return sugerenciaEntity;
    }

    /**
     * Obtiene la lista de los registros de Sugerencia que pertenecen a una
     * Temática.
     *
     * @param tematicasId id de la temática de la cual hacen parte las
     * sugerencias.
     * @return Colección de objetos de SugerenciaEntity.
     */
    public List<SugerenciaEntity> getSugerencias(Long tematicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sugerencias asociados a la temática con id = {0}", tematicasId);
        TematicaEntity tematicaEntity = tematicaPersistence.find(tematicasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las sugerencias asociados a la temática con id = {0}", tematicasId);
        return tematicaEntity.getSugerencias();
    }

    /**
     * Obtiene los datos de una instancia de Sugerencia a partir de su ID. La
     * existencia del elemento madre Temática se debe garantizar.
     *
     * @param tematicasId. El id de la temática buscada.
     * @param sugerenciasId. Identificador de la sugerencia a consultar.
     * @return Instancia de SugerenciaEntity con los datos de la sugerencia
     * consultada.
     *
     */
    public SugerenciaEntity getSugerencia(Long tematicasId, Long sugerenciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la sugerencia con id = {0} de la temática con id = {1}", new Object[]{sugerenciasId, tematicasId});
        return persistence.find(tematicasId, sugerenciasId);
    }

    /**
     * Actualiza la información de una instancia de Sugerencia.
     *
     * @param sugerenciaEntity Instancia de SugerenciaEntity con los nuevos
     * datos.
     * @param tematicasId id de la Tematica la cual será madre de la sugerencia
     * actualizada.
     * @return Instancia de SugerenciaEntity con los datos actualizados.
     * @throws BusinessLogicException si sugerenciaEntity tiene más caracteres
     * que los permitidos (20000).
     */
    public SugerenciaEntity updateSugerencia(Long tematicasId, SugerenciaEntity sugerenciaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la sugerencia con id = {0} de la temática con id = {1}", new Object[]{sugerenciaEntity.getId(), tematicasId});
        if (sugerenciaEntity.getComentario().length() > 20000) {
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 20000 caracteres");
        }
        TematicaEntity tematicaEntity = tematicaPersistence.find(tematicasId);
        sugerenciaEntity.setTematica(tematicaEntity);
        persistence.update(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la sugerencia con id = {0} de la temática con id = {1}", new Object[]{sugerenciaEntity.getId(), tematicasId});
        return sugerenciaEntity;
    }

    /**
     * Borrar una sugerencia de la base de datos.
     *
     * @param sugerenciasId. ID de la sugerencia a borrar.
     * @param tematicasId id de la temática la cual es madre de la Sugerencia.
     * @throws BusinessLogicException si la sugerencia no está asociada a la
     * temática.
     */
    public void deleteSugerencia(Long tematicasId, Long sugerenciasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la sugerencia con id = {0} de la temática con id = {1}", new Object[]{sugerenciasId, tematicasId});
        SugerenciaEntity old = getSugerencia(tematicasId, sugerenciasId);
        if (old == null) {
            throw new BusinessLogicException("La sugerencia con id = " + sugerenciasId + " no esta asociada a la temática con id = " + tematicasId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la sugerencia con id = {0} de la temática con id = {1}", new Object[]{sugerenciasId, tematicasId});
    }
}
