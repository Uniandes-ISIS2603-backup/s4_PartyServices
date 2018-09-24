/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Stateless
public class ValoracionPersistence {

    private static final Logger LOGGER = Logger.getLogger(ValoracionPersistence.class.getName());

    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    /**
     * Método para persistir la entidad en la base de datos.
     *
     * @param valoracionEntity objeto valoracion que se creará en la base de
     * datos.
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ValoracionEntity create(ValoracionEntity valoracionEntity) {
        LOGGER.log(Level.INFO, "Creando una valoracion nueva");
        em.persist(valoracionEntity);
        LOGGER.log(Level.INFO, "valoracion creada");
        return valoracionEntity;
    }

    /**
     * Actualiza una valoracion.
     *
     * @param valoracionEntity: la sugerencia que viene con los nuevos cambios.
     * Por ejemplo el comentario pudo cambiar. En ese caso, se haria uso del
     * método update.
     * @return una valoracion con los cambios aplicados.
     */
    public ValoracionEntity update(ValoracionEntity valoracionEntity) {

        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Actualizando valoracion con id = {0}", valoracionEntity.getId());
        return em.merge(valoracionEntity);
    }

    /**
     *
     * Borra una valoracion de la base de datos recibiendo como argumento el id
     * de la valoracion
     *
     * @param valoracionId: id correspondiente a la valoraciob a borrar.
     */
    public void delete(Long valoracionId) {
        LOGGER.log(Level.INFO, "Borrando valoracion con id = {0}", valoracionId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        ValoracionEntity entity = em.find(ValoracionEntity.class, valoracionId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la valoracion con id = {0}", valoracionId);
    }

    /**
     * Buscar una valoracion
     *
     * Busca si hay alguna valoracion asociada a un proveedor y con un ID
     * específico.
     *
     * @param proveedorId El ID del proveedor con respecto al cual se busca.
     * @param valoracionId El ID de la valoracion buscada.
     * @return La valoracion encontrada o null. Nota: Si existe una o más
     * valroaciones devuelve siempre la primera que encuentra.
     */
    public ValoracionEntity find(Long proveedorId, Long valoracionId) {
        LOGGER.log(Level.INFO, "Consultando la valoracion con id = {0} del proveedor con id = " + proveedorId, valoracionId);

        TypedQuery<ValoracionEntity> q = em.createQuery("select p from ValoracionEntity p where (p.proveedor.id = :proveedorId) and (p.id = :valoracionId)", ValoracionEntity.class);
        q.setParameter("proveedorId", proveedorId);
        q.setParameter("valoracionId", valoracionId);
        List<ValoracionEntity> results = q.getResultList();
        ValoracionEntity valoracion = null;
        if (results == null) {
            valoracion = null;
        } else if (results.isEmpty()) {
            valoracion = null;
        } else if (results.size() >= 1) {
            valoracion = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la valoracion con id = {0} del proveedor con id = " + proveedorId, valoracionId);
        return valoracion;
    }

    /**
     * Devuelve todas las valoraciones de la base de datos.
     *
     * @return una lista con todass las valoraciones que encuentre en la base de
     * datos, "select u from ValoracionEntity u" es como un "select * from
     * ValoracionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ValoracionEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos las valoraciones");

        TypedQuery query = em.createQuery("select u from ValoracionEntity u", ValoracionEntity.class);
        List<ValoracionEntity> respuesta = query.getResultList();
        return respuesta;

    }

}
