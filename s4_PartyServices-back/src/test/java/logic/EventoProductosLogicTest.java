/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.EventoProductosLogic;
import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
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
 * @author Tomas Vargas
 */

@RunWith(Arquillian.class)
public class EventoProductosLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLogic eventoLogic;
    @Inject
    private EventoProductosLogic eventoProductosLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    private List<ProductoEntity> productosData = new ArrayList();

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
        em.createQuery("delete from ProductoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProductoEntity productos = factory.manufacturePojo(ProductoEntity.class);
            em.persist(productos);
            productosData.add(productos);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                productosData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Productos existente a un Evento.
     */
    @Test
    public void addProductosTest() {
        EventoEntity entity = data.get(0);
        ProductoEntity productoEntity = productosData.get(1);
        ProductoEntity response = eventoProductosLogic.addProducto(entity.getNombre(),productoEntity.getNombre());

        Assert.assertNotNull(response);
        Assert.assertEquals(productoEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Productos asociadas a una
     * instancia Evento.
     */
    @Test
    public void getProductosTest() {
        List<ProductoEntity> list = eventoProductosLogic.getProductos(data.get(0).getNombre());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Productos asociada a una instancia
     * Evento.     *
     * @throws co.edu.uniandes.csw.productostore.exceptions.BusinessLogicException
     */
    @Test
    public void getProductoTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        ProductoEntity productoEntity = productosData.get(0);
        ProductoEntity response = eventoProductosLogic.getProducto(entity.getNombre(), productoEntity.getNombre());

        Assert.assertEquals(productoEntity.getId(), response.getId());
        Assert.assertEquals(productoEntity.getNombre(), response.getNombre());
        Assert.assertEquals(productoEntity.getDuenio(), response.getDuenio());
    }

    /**
     * Prueba para obtener una instancia de Productos asociada a una instancia
     * Evento que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.productostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getProductoNoAsociadoTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        ProductoEntity productoEntity = productosData.get(1);
        eventoProductosLogic.getProducto(entity.getEstado(), productoEntity.getNombre());
    }

    /**
     * Prueba para remplazar las instancias de Productos asociadas a una instancia
     * de Evento.
     */
    @Test
    public void replaceProductosTest() {
        EventoEntity entity = data.get(0);
        List<ProductoEntity> list = productosData.subList(1, 3);
        eventoProductosLogic.replaceProductos(entity.getNombre(), list);

        entity = eventoLogic.getEvento(entity.getId());
        Assert.assertFalse(entity.getProductos().contains(productosData.get(0)));
        Assert.assertTrue(entity.getProductos().contains(productosData.get(1)));
        Assert.assertTrue(entity.getProductos().contains(productosData.get(2)));
    }
}
