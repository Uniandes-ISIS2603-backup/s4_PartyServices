/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ProductoLogic;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ProductoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ProductoLogic productoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ProductoEntity> data = new ArrayList<ProductoEntity>();
    
    private List<EventoEntity> eventoData = new ArrayList();
    
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
            entity.setEvento(eventoData.get(0));
            em.persist(entity);
            data.add(entity);
        }
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        em.persist(proveedor);
        //proveedor.getCatalogoProductos().add(data.get(1));
        data.get(1).setProveedor(proveedor);
    }
    
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
    
    @Test
    public void deleteProductoTest() {
        
    }
    
    @Test
    public void updateProductoTest() {
        
    }
    
}
