/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
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
public class ValoracionLogic {

    private static final Logger LOGGER = Logger.getLogger(ValoracionLogic.class.getName());

    @Inject
    private ValoracionPersistence persistence;

    @Inject
    private ProveedorPersistence proveedorPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Crea una valoración en la base de datos.
     *
     * @param proveedorId. ID del proveedor el cual será padre de la nueva
     * valoracion.
     * @param valoracionEntity La entidad que representa la valoracion a
     * persistir.
     * @return La entidad de la valoración luego de persistirla.
     * @throws BusinessLogicException si el tamaño de la valoración es mayor a
     * los 10000 caracteres.
     */
    public ValoracionEntity createValoracion(Long proveedorId, ValoracionEntity valoracionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la valoracion");

        
        if ((valoracionEntity.getTitulo() != null && valoracionEntity.getTitulo().length() > 50)||(valoracionEntity.getTitulo() == null)||(valoracionEntity.getTitulo().equals("")) ) {
            throw new BusinessLogicException("El tamaño del titulo no debe ser superior a los 50 caracteres o vacío");
        }
        if ((valoracionEntity.getComentario() != null && valoracionEntity.getComentario().length() > 1000) ||(valoracionEntity.getComentario()== null)||(valoracionEntity.getComentario().equals(""))) {
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 1000 caracteres o vacío");
        }
        if (valoracionEntity.getPuntaje() != null && (valoracionEntity.getPuntaje() > 10 ||valoracionEntity.getPuntaje() < 0)) {
            throw new BusinessLogicException("El puntaje no puede ser mayor que 10 o menor que 0");
        }
        ProveedorEntity proveedor = proveedorPersistence.find(proveedorId);
        valoracionEntity.setProveedor(proveedor);

        if (valoracionEntity.getCliente() != null) {
            ClienteEntity cliente = clientePersistence.find(valoracionEntity.getCliente().getId());
            valoracionEntity.setCliente(cliente);
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la valoracion");
        return persistence.create(valoracionEntity);
    }

    /**
     * Obtiene la lista de los registros de valoracion que pertenecen a un
     * proveedor.
     *
     * @param proveedorId id del proveedor del cual hacen parte las
     * valoraciones.
     * @return Colección de objetos de ValoracionEntity.
     */
    public List<ValoracionEntity> getValoraciones(Long proveedorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las valoraciones asociadass al proveedor con id = {0}", proveedorId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedorId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las sugerencias asociados a la temática con id = {0}", proveedorId);
        return (List<ValoracionEntity>) proveedorEntity.getValoraciones();
    }

    /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID. La
     * existencia del elemento padre Proveedor se debe garantizar.
     *
     * @param proveedorId. El id del proveedor buscado.
     * @param valoracionId. Identificador de la valoracion a consultar.
     * @return Instancia de ValoracionEntity con los datos de la valoracion
     * consultada.
     *
     */
    public ValoracionEntity getValoracion(Long proveedorId, Long valoracionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la valoracion con id = {0} del proveedor con id = {1}", new Object[]{valoracionId, proveedorId});
        return persistence.find(proveedorId, valoracionId);
    }

    /**
     * Actualiza la información de una instancia de Valoracion.
     *
     * @param valoracionEntity Instancia de ValoracionEntity con los nuevos
     * datos.
     * @param proveedorId. ID del proveedor el cual será padre de la valoracion
     * actualizada.
     * @return Instancia de ValroacionEntity con los datos actualizados.
     * @throws BusinessLogicException si valoracionEntity tiene más caracteres
     * que los permitidos (20000).
     */
    public ValoracionEntity updateValoracion(Long proveedorId, ValoracionEntity valoracionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la valoracion con id = {0} del proveedor con id = {1}", new Object[]{valoracionEntity.getId(), proveedorId});
        if (valoracionEntity.getComentario().length() > 20000) {
            throw new BusinessLogicException("El tamaño del texto no debe ser superior a los 20000 caracteres");
        }
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedorId);
        valoracionEntity.setProveedor(proveedorEntity);

        ValoracionEntity newEntity = persistence.update(valoracionEntity);

        LOGGER.log(Level.INFO, "Termina proceso de actualizar la valoracion con id = {0} del proveedor con id = {1}", new Object[]{valoracionEntity.getId(), proveedorId});
        return newEntity;
    }

    /**
     * Borrar una valoracion de la base de datos.
     *
     * @param valoracionId. ID de la valoracion a borrar.
     * @param proveedorId. ID del proveedor la cual es padre de la valoracion.
     * @throws BusinessLogicException si la valroacion no está asociada al
     * proveedor.
     */
    public void deleteValoracion(Long proveedorId, Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la valoracion con id = {0} del proveedor con id = {1}", new Object[]{valoracionId, proveedorId});
        ValoracionEntity old = getValoracion(proveedorId, valoracionId);
        if (old == null) {
            throw new BusinessLogicException("La valoracion con id = " + valoracionId + " no esta asociada al proveedor con id = " + proveedorId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la valoracion con id = {0} del proveedor con id = {1}", new Object[]{valoracionId, proveedorId});
    }

}
