/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * 
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Producto.
 * @author Tomas Vargas
 */
@Stateless
public class EventoProductosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EventoProductosLogic.class.getName());

    @Inject
    private ProductoPersistence productoPersistence;

    @Inject
    private EventoPersistence eventoPersistence;

    /**
     * Agregar un producto a la evento
     *
     * @param productosId El id producto a guardar
     * @param eventosId El id de la evento en la cual se va a guardar el
     * producto.
     * @return El producto creado.
     */
    public ProductoEntity addProducto(Long productosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un producto a la evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        ProductoEntity productoEntity = productoPersistence.find(productosId);
        productoEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un producto a la evento con id = {0}", eventosId);
        return productoEntity;
    }

    /**
     * Retorna todos los productos asociados a una evento
     *
     * @param eventosId El ID de la evento buscada
     * @return La lista de productos de la evento
     */
    public List<ProductoEntity> getProductos(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los productos asociados a la evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getProductos();
    }

    /**
     * Retorna un producto asociado a una evento
     *
     * @param eventosId El id de la evento a buscar.
     * @param productosId El id del producto a buscar
     * @return El producto encontrado dentro de la evento.
     * @throws BusinessLogicException Si el producto no se encuentra en la
     * evento
     */
    public ProductoEntity getProducto(Long eventosId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto con id = {0} de la evento con id = " + eventosId, productosId);
        List<ProductoEntity> productos = eventoPersistence.find(eventosId).getProductos();
        ProductoEntity productoEntity = productoPersistence.find(productosId);
        int index = productos.indexOf(productoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el producto con id = {0} de la evento con id = " + eventosId, productosId);
        if (index >= 0) {
            return productos.get(index);
        }
        throw new BusinessLogicException("El producto no está asociado a la evento");
    }

    /**
     * Desasocia un Producto existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param productosId Identificador de la instancia de Producto
     */
    public void removeProducto(Long eventosId, Long productosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un producto del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        ProductoEntity productoEntity = productoPersistence.find(productosId);
        productoEntity.getEventos().remove(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un producto del evento con id = {0}", eventosId);
    }
}
