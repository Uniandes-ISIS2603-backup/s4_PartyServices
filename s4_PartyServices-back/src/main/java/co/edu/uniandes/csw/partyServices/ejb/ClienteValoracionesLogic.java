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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Cliente y Sugerencia.
 * 
 * @author Jesús Orlando Cárcamo Posada y Elías Negrete
 */
@Stateless
public class ClienteValoracionesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteValoracionesLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ValoracionPersistence valoracionPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Agregar una valoracion a un cliente.
     *
     * @param valoracionId. El ID de la valoracion a guardar
     * @param proveedorId. El ID del proveedor del cual hace parte la
     * valoracion.
     * @param clientesId. El ID del cliente al cual se le va a guardar la
     * valoracion.
     * @return La valoracion agregada al cliente.
     */
    public ValoracionEntity addValoracion(Long proveedorId, Long valoracionId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar la valoracion con id = {1}, que pertenece al proveedor con id = {2}, al cliente con id = {0}", new Object[]{clientesId, valoracionId, proveedorId});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        
        ValoracionEntity valoracionEntity = valoracionPersistence.find(proveedorId, valoracionId);
        valoracionEntity.setCliente(clienteEntity);
        valoracionEntity.setNombreUsuario(clienteEntity.getNombreUsuario());
        ValoracionEntity actualizado = valoracionPersistence.update(valoracionEntity);
        //creo que falta merge aquí
        LOGGER.log(Level.INFO, "Termina proceso de asociar la valoracion con id = {1}, que pertenece al proveedor con id = {2}, al cliente con id = {0}", new Object[]{clientesId, valoracionId, proveedorId});
        return actualizado;
    }
    
    /**
     * Retorna todas las valoraciones asociadas a un cliente.
     *
     * @param clientesId El ID del cliente buscado
     * @return La lista de valoraciones del cliente.
     */
    public List<ValoracionEntity> getValoraciones(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las valoraciones asociadas al cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getValoraciones();
    }
    
    /**
     *
     * Obtener una valoracion asociada a un cliente por medio del ID de esta y
     * el ID del proveedor al que pertenece.
     *
     * @param clientesId. El id del cliente a buscar.
     * @param valoracionId. ID de la valoracion a buscar.
     * @param proveedorId. El ID del proveedor del cual hace parte la
     * valoracion.
     * @return La valoracion del cliente solicitada.
     * @throws BusinessLogicException Si la valoracion no se encuentra asociada al
     * cliente.
     */
    public ValoracionEntity getValoracion(Long proveedorId, Long valoracionId, Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la valoracion con id = {1}, que pertenece al proveedor con id = {2}, del cliente con id = {0}", new Object[]{clientesId, valoracionId, proveedorId});
        List<ValoracionEntity> valoraciones = clientePersistence.find(clientesId).getValoraciones();
        ValoracionEntity valoracionEntity = valoracionPersistence.find(proveedorId, valoracionId);
        int index = valoraciones.indexOf(valoracionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la valoracion con id = {1}, que pertenece al proveedor con id = {2}, del cliente con id = {0}", new Object[]{clientesId, valoracionId, proveedorId});
        if (index >= 0) {
            return valoraciones.get(index);
        }
        throw new BusinessLogicException("La valoracion no está asociada al cliente");
    }
    
    /**
     * Remueve la relación entre un cliente y sus valoraciones.
     *
     * @param clientesId. El ID del cliente que tiene las valoraciones a las que se quiere desasociar. 
     */
    public void removeValoraciones(Long clientesId){
        LOGGER.log(Level.INFO, "Inicia proceso de remover la relación de las valoraciones con el cliente con id = {0}", clientesId);

        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        List<ValoracionEntity> valoracionList = valoracionPersistence.findAll();
        for (ValoracionEntity valoracion : valoracionList) {
            if (valoracion.getCliente() != null && valoracion.getCliente().equals(clienteEntity)) {
                valoracion.setCliente(null);
                valoracion.setNombreUsuario("Anonimo");
                valoracionPersistence.update(valoracion);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de remover la relación de las valoraciones con el cliente con id = {0}", clientesId);
    }
}
