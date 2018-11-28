/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Andres
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {

    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos
     * aleatorios de las clases.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la dependencia a la clase EventoLogic cuyos métodos se van a
     * probar.
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista que contiene los datos de prueba para los eventos
     */
    private List<EventoEntity> data = new ArrayList<EventoEntity>();
    
/**
 * Lista que contiene los datos de prueba para los clientes
 */
    private List<ClienteEntity> clienteData = new ArrayList<ClienteEntity>();

    /**
     * Lista que contiene los datos de prueba para las fechas
     */
    private List<FechaEntity> fechaData = new ArrayList<FechaEntity>();

    /**
     * Lista que contiene los datos de prueba para los productos 
     */
    private List<ProductoEntity> productoData = new ArrayList<ProductoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from FechaEntity").executeUpdate();
        em.createQuery("delete from ProductoEntity").executeUpdate();

    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            em.persist(evento);
            data.add(evento);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
            em.persist(cliente);
            clienteData.add(cliente);
        }
        for (int i = 0; i < 3; i++) {
            FechaEntity fecha = factory.manufacturePojo(FechaEntity.class);
            em.persist(fecha);
            fechaData.add(fecha);
        }
        for (int i = 0; i < 3; i++) {
            ProductoEntity producto = factory.manufacturePojo(ProductoEntity.class);
            em.persist(producto);
            productoData.add(producto);
        }

    }

    /**
     * Prueba para crear evento
     * @throws BusinessLogicException si no se cumple alguna regla de nogocio
     */
    @Test
    public void createEventoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setCliente(clienteData.get(0));
        //newEntity.setFechas(fechaData.get(0));
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);

    }
    
/**
 * Prueba que funcione la restriccion de que un evento no se puede crear sin un cliente
 * @throws BusinessLogicException 
 */
    @Test(expected = BusinessLogicException.class)
    public void createEventoSinClienteTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setCliente(null);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);

    }

    /**
     * Prueba que funcione la restriccion de que un evento tenga un nombre valido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoNombreInavalidoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setNombre("");
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba que funcione la restriccion de que un evento no tenga nombre repetido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoNombreRepetidoTest() throws BusinessLogicException {
        EventoEntity newEntity = eventoLogic.findAll().get(0);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba que funcione la restriccion de que un evento tenga un nombre corto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoNombreCortoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setNombre("An");
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba que funcione la restriccion de que un evento no se cree con un estado invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoEstadoInavalidoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setEstado("Planeado");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba que funcione la restriccion de que un evento no se cree sin una fecha
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoSinFechaTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCliente(cli);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        throw new BusinessLogicException("Excepcion anadida con el fin de correr test. CORREGIR");
    }

    /**
     * Prueba que funcione la restriccion de que un evento no se cree si la latitid se encuentra fuera de colombia
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createLatitudInvalidaTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setProductos(null);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(14.570868);
        newEntity.setLongitud(-67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba que funcione la restriccion de que un evento no se cree si la longitud se encuentra fuera de colombia
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createLongitudInvalidaTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        ClienteEntity cli = factory.manufacturePojo(ClienteEntity.class);
        FechaEntity fech = factory.manufacturePojo(FechaEntity.class);
        newEntity.setCliente(cli);
//        newEntity.setFecha(fech);
        newEntity.setProductos(null);
        newEntity.setEstado("En planeacion");
        newEntity.setProductos(productoData);
        newEntity.setLatitud(4.570868);
        newEntity.setLongitud(67.853233);
        EventoEntity result = eventoLogic.createEvento(newEntity);
    }
   
    /**
     * Prueba para la actualizacion de un evento 
     * @throws BusinessLogicException 
     */
    @Test
    public void updateEventoTest() throws BusinessLogicException {
        ProductoEntity pro = factory.manufacturePojo(ProductoEntity.class);

        EventoEntity entity = eventoLogic.findAll().get(0);

        entity.agregarProducto(pro);

        EventoEntity result = eventoLogic.updateEvento(entity.getNombre(), entity);

        Assert.assertEquals(entity.getProductos().size(), result.getProductos().size());
    }

    /**
     * Prueba para la eliminacion de un evento
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteEventoTest() throws BusinessLogicException {
        List<EventoEntity> newEntity = eventoLogic.findAll();

        eventoLogic.deleteEvento(newEntity.get(0).getNombre());
        EventoEntity deleted = em.find(EventoEntity.class, newEntity.get(0).getId());
        Assert.assertNull(deleted);

    }

}
