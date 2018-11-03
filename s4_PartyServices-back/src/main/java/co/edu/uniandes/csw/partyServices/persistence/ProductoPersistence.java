/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Andres
 */
@Stateless
public class ProductoPersistence {

    /**
     * Log para el registro de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(ProductoPersistence.class.getName());

    /**
     * Manejador de entidades
     */
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param pProductoEntity
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ProductoEntity create(ProductoEntity pProductoEntity) {
        LOGGER.log(Level.INFO, "Creando un nuevo producto");

        em.persist(pProductoEntity);

        LOGGER.log(Level.INFO, "Saliendo de crear un producto");

        return pProductoEntity;

    }

    /**
     * Devuelve todos los productos de la base de datos.
     *
     * @return una lista con todos los productos que encuentre en la base de
     * datos
     */
    public List<ProductoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los productos");

        TypedQuery query = em.createQuery("select u from ProductoEntity u", ProductoEntity.class);

        return query.getResultList();

    }

    /**
     * Busca si hay algun producto con el id que se envía de argumento
     *
     * @param pProductoId: id correspondiente al producto buscado
     * @return El producto con el Id dado
     */
    public ProductoEntity find(Long pProductoId) {

        LOGGER.log(Level.INFO, "Buscando el producto solicitado");

        return em.find(ProductoEntity.class, pProductoId);

    }

    /**
     * Borra de la base de datos el producto con el Id enviado por parametro
     *
     * @param pProductoId
     */
    public void delete(Long pProductoId) {
        LOGGER.log(Level.INFO, "Borrando producto solicitado");

        ProductoEntity entity = find(pProductoId);

        em.remove(entity);

        LOGGER.log(Level.INFO, "Saliendo de borrar el producto solicitado");

    }

    /**
     * Acualiza un producto
     *
     * @param pProductoEntity: objeto con el cual se modificará el existente
     * @return el producto actualizado
     */
    public ProductoEntity update(ProductoEntity pProductoEntity) {

        LOGGER.log(Level.INFO, "Actualizando el producto solicitado");

        LOGGER.log(Level.INFO, "Saliendo de actualizar el producto solicitado");

        return em.merge(pProductoEntity);

    }

    /**
     * Busca un producto por su nombre
     *
     * @param nombre: nombre del producto que se quiere buscar
     * @return objeto del producto encontrado
     */
    public ProductoEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando producto por nombre ", nombre);
        TypedQuery query = em.createQuery("Select e From ProductoEntity e where e.nombre = :nombre", ProductoEntity.class);

        query = query.setParameter("nombre", nombre);

        List<ProductoEntity> sameName = query.getResultList();
        ProductoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar producto por nombre ", nombre);
        return result;

    }
}
