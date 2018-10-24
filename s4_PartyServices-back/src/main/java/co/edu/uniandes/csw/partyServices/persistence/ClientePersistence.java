/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Cliente. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Elias Negrete
 */
@Stateless
public class ClientePersistence {


    /*
    *Log para el registro de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());

    /*
    *manejador de entidades 
     */
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param pClienteEntity objeto cliente que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ClienteEntity create(ClienteEntity pClienteEntity) {
        LOGGER.log(Level.INFO, "Se está creando un nuevo cliente");

        em.persist(pClienteEntity);

        LOGGER.log(Level.INFO, "Se creó un nuevo cliente");

        return pClienteEntity;

    }

    /**
     * Devuelve todos los clientes de la base de datos.
     *
     * @return una lista con todos los clientes que encuentre en la base de
     * datos, "select u from ClienteEntity u" es como un "select * from
     * ClienteEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ClienteEntity> findAll() {
        LOGGER.log(Level.INFO, "Se busca todos los clientes");

        Query q = em.createQuery("select u from ClienteEntity u");
        return q.getResultList();

    }

    /**
     * Busca si hay algun cliente con el id que se envía de argumento
     *
     * @param pClienteId : id correspondiente al cliente buscado.
     * @return un cliente.
     */
    public ClienteEntity find(Long pClienteId) {

        LOGGER.log(Level.INFO, "Se busca el cliente en base al ID solicitado");

        return em.find(ClienteEntity.class, pClienteId);

    }

    /**
     *
     * Borra un cliente de la base de datos recibiendo como argumento el id del
     * cliente
     *
     * @param pClienteId : id correspondiente al cliente a borrar.
     */
    public void delete(Long pClienteId) {
        LOGGER.log(Level.INFO, "Se borra al cliente en base del Id solicitado");

        ClienteEntity entityCliente = em.find(ClienteEntity.class, pClienteId);

        em.remove(entityCliente);

        LOGGER.log(Level.INFO, "Se ha borrado al cliente en base del Id solicitado");

    }

    /**
     * Actualiza un cliente.
     *
     * @param clienteEntity : el cliente que viene con los nuevos cambios. Por
     * ejemplo el login pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un cliente con los cambios aplicados.
     */
    public ClienteEntity update(ClienteEntity clienteEntity) {

        LOGGER.log(Level.INFO, "Se actualiza el cliente solicitado");

        LOGGER.log(Level.INFO, "Se ha actualizado el cliente solicitado");
        return em.merge(clienteEntity);

    }

    /**
     * Busca si hay algun libro con el login que se envía de argumento
     *
     * @param login: login del cliente que se está buscando
     * @return null si no existe ningun libro con el login del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ClienteEntity findByLogin(String login) {
        LOGGER.log(Level.INFO, "Se consulta el cliente con usuario = {0}  ", login);
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.login = :login", ClienteEntity.class);

        query = query.setParameter("login", login);

        List<ClienteEntity> sameName = query.getResultList();
        ClienteEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Se han consultado el cliente con usuario = {0}  ", login);
        return result;

    }

}
