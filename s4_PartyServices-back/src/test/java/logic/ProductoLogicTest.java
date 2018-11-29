/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
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
public class ProductoLogicTest {

    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos
     * aleatorios de las clases.
     */
    private PodamFactory factory = new PodamFactoryImpl();

     /**
     * Inyección de la dependencia a la clase ProductoLogic cuyos métodos se van a
     * probar.
     */
    @Inject
    private ProductoLogic productoLogic;

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
     * Lista que contiene los datos de prueba para los productos
     */
    private List<ProductoEntity> data = new ArrayList<ProductoEntity>();

    /**
     * Lista que contiene los datos de prueba para los eventos
     */
    private List<EventoEntity> eventoData = new ArrayList();

    /**
     * Lista que contiene los datos de prueba para los proveedores
     */
    private List<ProveedorEntity> proveedorData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProductoEntity.class.getPackage())
                .addPackage(ProductoLogic.class.getPackage())
                .addPackage(ProductoPersistence.class.getPackage())
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
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            em.persist(evento);
            eventoData.add(evento);
        }
        for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(proveedor);
            proveedorData.add(proveedor);
        }
        for (int i = 0; i < 3; i++) {
            ProductoEntity entity = factory.manufacturePojo(ProductoEntity.class);
            if (i != 1) {
                entity.setEvento(eventoData.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        em.persist(proveedor);
        data.get(1).setProveedor(proveedor);
    }
/**
 * Prueba para crear producto sin errores 
 * @throws BusinessLogicException 
 */
    @Test
    public void createProductoTest() throws BusinessLogicException {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba");
        newEntity.setCosto(3000);
        newEntity.setCantidad(100);
        ProductoEntity result = productoLogic.createProducto(newEntity);
        Assert.assertNotNull(result);

    }
/**
 *  Prueba que funcione la restriccion de que un producto no se pueda crear con un nombre vacio
 * @throws BusinessLogicException 
 */
    @Test(expected = BusinessLogicException.class)
    public void createProductoNombreInvalidoTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("");
        newEntity.setCosto(3000);
        newEntity.setCantidad(100);
        ProductoEntity result = productoLogic.createProducto(newEntity);

    }
/**
 *  Prueba que funcione la restriccion de que un producto no se pueda crear si ya esxiste alguno
 * con el mismo nombre
 * @throws BusinessLogicException 
 */
    @Test(expected = BusinessLogicException.class)
    public void createProductoNombreExistenteTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba");
        newEntity.setCosto(3000);
        newEntity.setCantidad(100);
        ProductoEntity result = productoLogic.createProducto(newEntity);
        
        
        ProductoEntity newEntity1 = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba");
        newEntity.setCosto(300);
        newEntity.setCantidad(20);
        ProductoEntity result1 = productoLogic.createProducto(newEntity);
        
        
        
    }

    /**
     *  Prueba que funcione la restriccion de que un producto no se pueda crear si su nombre no cumple con la cantidad
     * necesaria de numero de caracteres
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createProductoNombreNumerosDeCaracteresTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("A");
        newEntity.setCosto(3100);
        newEntity.setCantidad(2);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }

   // @Test(expected = BusinessLogicException.class)
    public void createProductoNombreCaracteresEspecialesTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("(/&%9$#");
        newEntity.setCosto(30);
        newEntity.setCantidad(5);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }
    
/**
 *  Prueba que funcione la restriccion de que un producto no se pueda crear si no tiene un proveedor o dueño
 * @throws BusinessLogicException 
 */
    @Test(expected = BusinessLogicException.class)
    public void createProductoDuenioInvalidoTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(null);
        newEntity.setNombre("Producto de prueba sin duenio");
        newEntity.setCosto(500);
        newEntity.setCantidad(3);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }

    /**
     *  Prueba que funcione la restriccion de que un producto no pueda tener un costo invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createProductoCostoInvalidoTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba costo invalido");
        newEntity.setCosto(-300);
        newEntity.setCantidad(10);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }

    /**
     *  Prueba que funcione la restriccion de que un producto no pueda tener una cantidad invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createProductoCantidadInvalidoTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba cantidad invalida");
        newEntity.setCosto(3500);
        newEntity.setCantidad(-1);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }


    /**
     *  Prueba que funcione la restriccion de que un producto no tenga mas numero de eventos 
     * que cantdad del producto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createProductoCantidadDeEventosInvalidosTest() throws BusinessLogicException 
    {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        newEntity.setEvento(eventoData.get(0));
        newEntity.setProveedor(proveedorData.get(0));
        newEntity.setNombre("Producto de prueba mas eventos");
        newEntity.setCosto(666);
        newEntity.setCantidad(0);
        ProductoEntity result = productoLogic.createProducto(newEntity);
    }
/**
 * Prueba para eliminar un producto
 * @throws BusinessLogicException 
 */
    @Test
    public void deleteProductoTest() throws BusinessLogicException {
        ProductoEntity entity = data.get(1);
        productoLogic.deleteProducto(entity.getNombre());
        ProductoEntity deleted = em.find(ProductoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar producto que aun tiene eventos asignados
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteProductoConEventosTest() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);
        productoLogic.deleteProducto(entity.getNombre());
    }

    /**
     * Prueba para actualizar un producto
     * @throws BusinessLogicException 
     */
    @Test
    public void updateProductoTest() throws BusinessLogicException {
        int num = 10;

        data.get(0).setCantidad(num);

        ProductoEntity entity = data.get(0);

        ProductoEntity result = productoLogic.updateProducto(entity.getNombre(), entity);

        Assert.assertEquals(num, result.getCantidad());

    }

    /**
     * Prueba para actualizar un producto con un nombre invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateProductoNombreInvalidoTest() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);

        ProductoEntity result = productoLogic.updateProducto("", entity);
    }
    
    /**
     * Prueba para consultar un producto.
     */
    @Test
    public void getProductoTest() {
        ProductoEntity entity = data.get(0);
        ProductoEntity resultEntity = productoLogic.getProductoId(entity.getId());
        resultEntity = productoLogic.getProducto(entity.getNombre());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCosto(), resultEntity.getCosto());
        Assert.assertEquals(entity.getCantidad(), resultEntity.getCantidad());
        Assert.assertEquals(entity.getDuenio(), resultEntity.getDuenio());
        Assert.assertEquals(entity.getEventos(), resultEntity.getEventos());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getProductoTest4() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);

        ProductoEntity result = productoLogic.createProducto(entity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getProductoTest5() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);
        entity.setCantidad(-1);

        ProductoEntity result = productoLogic.createProducto(entity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getProductoTest6() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);
        entity.setProveedor(null);

        ProductoEntity result = productoLogic.createProducto(entity);
    }
    
    public void getProductoTest7() throws BusinessLogicException {
        ProductoEntity entity = data.get(0);

        ProductoEntity result = productoLogic.createProducto(entity);
        Assert.assertTrue(productoLogic.findAll().size() >= 0);
    }
}
