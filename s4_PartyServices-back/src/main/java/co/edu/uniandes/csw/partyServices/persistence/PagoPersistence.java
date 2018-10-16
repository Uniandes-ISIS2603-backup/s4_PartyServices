/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Pago. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author estudiante
 */
@Stateless
public class PagoPersistence {

    /*
    * Log de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(PagoPersistence.class.getName());

    /**
     * manejador de entidades
     */
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    /**
     * Crear un pago
     *
     * Crea un nuevo pago con la información recibida en la entidad.
     *
     * @param pPagoEntity La entidad que representa el nuevo pago
     * @return La entidad creada
     */
    public PagoEntity create(PagoEntity pPagoEntity) {
        LOGGER.log(Level.INFO, "Se está creando un nuevo pago");

        em.persist(pPagoEntity);

        LOGGER.log(Level.INFO, "Se creó un nuevo pago");

        return pPagoEntity;

    }

    /**
     * Método utilizado para buscar todos los pagos de la base de datos
     *
     * @return una lista con con todas las entidades-pago
     */
    public List<PagoEntity> findAll() {
        LOGGER.log(Level.INFO, "Se busca todos los pagos");

        TypedQuery query = em.createQuery("select u from PagoEntity u", PagoEntity.class);

        return query.getResultList();

    }

    /**
     * Buscar un pago
     *
     * Busca si hay algun pago asociado a un cliente y con un ID específico
     *
     * @param clientesID El ID del cliente con respecto al cual se busca
     * @param pagosId El ID del pago buscado
     * @return El pago encontrado o null. Nota: Si existe uno o más pagos
     * devuelve siempre el primera que encuentra
     */
    public PagoEntity find(Long clientesID, Long pagosId) {

        LOGGER.log(Level.INFO, "Consultando el pago con id = {0} del cliente con id = ");
        TypedQuery<PagoEntity> q = em.createQuery("select p from PagoEntity p where (p.cliente.id = :clienteid) and (p.id = :pagosId)", PagoEntity.class);
        q.setParameter("clienteid", clientesID);
        q.setParameter("pagosId", pagosId);
        List<PagoEntity> results = q.getResultList();
        PagoEntity review = null;
        if (!results.isEmpty()) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el review con id = {0} del libro con id =");
        return review;

    }

    /**
     * Eliminar un pago
     *
     * Elimina el pago asociado al ID que recibe
     *
     * @param pPagoId El ID del pago que se desea borrar
     */
    public void delete(Long pPagoId) {
        LOGGER.log(Level.INFO, "Se borra al pago en base del Id solicitado");

        PagoEntity entityPago = em.find(PagoEntity.class, pPagoId);

        em.remove(entityPago);

        LOGGER.log(Level.INFO, "Se ha borrado al pago en base del Id solicitado");

    }

    /**
     * Actualizar un pago
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param pagoEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public PagoEntity update(PagoEntity pagoEntity) {

        LOGGER.log(Level.INFO, "Se actualiza el pago solicitado");

        LOGGER.log(Level.INFO, "Se ha actualizado el pago solicitado");
        return em.merge(pagoEntity);

    }

    /**
     * Se busca un pago a través del login de su cliente.
     *
     * @param pUsuario el login del usuario buscado
     * @return result La entidad resultante de la busqueda
     */
    public PagoEntity findByUsuario(String pUsuario) {
        LOGGER.log(Level.INFO, "Consultando el pago por el cliente con usuario = {0} ", pUsuario);
        TypedQuery query = em.createQuery("Select e From PagoEntity e where e.usuario = :usuario", PagoEntity.class);

        query = query.setParameter("usuario", pUsuario);

        List<PagoEntity> sameName = query.getResultList();
        PagoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Se han consultado el pago por el cliente con usuario = {0} ", pUsuario);
        return result;

    }

}
