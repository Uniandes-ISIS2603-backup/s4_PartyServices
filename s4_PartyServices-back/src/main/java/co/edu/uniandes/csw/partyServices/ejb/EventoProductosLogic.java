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
     * Asocia un Producto existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param productosId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Evento
     */
    public ProductoEntity addProducto(Long eventosId, Long productosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un producto al autor con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        ProductoEntity productoEntity = productoPersistence.find(productosId);
        productoEntity.getEventos().add(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un producto al autor con id = {0}", eventosId);
        return productoPersistence.find(productosId);
    }

    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de ProductoEntity asociadas a la instancia de
     * Evento
     */
    public List<ProductoEntity> getProductos(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos del autor con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getProductos();
    }

    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param productosId Identificador de la instancia de Producto
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el producto no está asociado al autor
     */
    public ProductoEntity getProducto(Long eventosId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto con id = {0} del autor con id = " + eventosId, productosId);
        List<ProductoEntity> productos = eventoPersistence.find(eventosId).getProductos();
        ProductoEntity productoEntity = productoPersistence.find(productosId);
        int index = productos.indexOf(productoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el producto con id = {0} del autor con id = " + eventosId, productosId);
        if (index >= 0) {
            return productos.get(index);
        }
        throw new BusinessLogicException("El producto no está asociado al autor");
    }

    /**
     * Remplaza las instancias de Producto asociadas a una instancia de Evento
     *
     * @param eventoId Identificador de la instancia de Evento
     * @param productos Colección de instancias de ProductoEntity a asociar a instancia
     * de Evento
     * @return Nueva colección de ProductoEntity asociada a la instancia de Evento
     */
    public List<ProductoEntity> replaceProductos(Long eventoId, List<ProductoEntity> productos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los productos asocidos al evento con id = {0}", eventoId);
        EventoEntity eventoEntity = eventoPersistence.find(eventoId);
        List<ProductoEntity> productoList = productoPersistence.findAll();
        for (ProductoEntity producto : productoList) {
            if (productos.contains(producto)) {
                if (!producto.getEventos().contains(eventoEntity)) {
                    producto.getEventos().add(eventoEntity);
                }
            } else {
                producto.getEventos().remove(eventoEntity);
            }
        }
        eventoEntity.setProductos(productos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los productos asocidos al evento con id = {0}", eventoId);
        return eventoEntity.getProductos();
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
