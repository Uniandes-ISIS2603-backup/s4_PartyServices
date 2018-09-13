/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.NotificacionLogic;
import co.edu.uniandes.csw.partyServices.ejb.ProveedorLogic;
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.NotificacionPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
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
public class ProveedorLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProveedorLogic proveedorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProveedorEntity> data = new ArrayList<ProveedorEntity>();

    private String mensajeLargo;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorLogic.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
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
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 6; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
}
      /**
     * Prueba para crear una Notificacion.
     */
    @Test
     public void createProveedorTest() throws BusinessLogicException {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = proveedorLogic.createProveedor(newEntity);
        Assert.assertNotNull(result);
        ProveedorEntity entity = em.find(ProveedorEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());;
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());;
        Assert.assertEquals(newEntity.getContrasenia(), entity.getContrasenia());;
        Assert.assertEquals(newEntity.getAgenda(), entity.getAgenda());
        Assert.assertEquals(newEntity.getCatalogoProductos(), entity.getCatalogoProductos());
        Assert.assertEquals(newEntity.getNotificaciones(), entity.getNotificaciones());
        Assert.assertEquals(newEntity.getValoraciones(), entity.getValoraciones());

     }
     
     @Test
     public void UpdateProveedorTest() throws BusinessLogicException {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity anotherEntity = factory.manufacturePojo(ProveedorEntity.class);
        long idnot = anotherEntity.getId();
        ProveedorEntity result = proveedorLogic.updateProveedor(idnot, newEntity);
        
        Assert.assertNotNull(result);
        ProveedorEntity entity = em.find(ProveedorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(newEntity.getAgenda(), entity.getAgenda());
        Assert.assertEquals(newEntity.getCatalogoProductos(), entity.getCatalogoProductos());
        Assert.assertEquals(newEntity.getNotificaciones(), entity.getNotificaciones());
        Assert.assertEquals(newEntity.getValoraciones(), entity.getValoraciones());
     }
     
}
