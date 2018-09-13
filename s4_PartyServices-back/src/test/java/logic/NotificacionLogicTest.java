/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.partyServices.ejb.NotificacionLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.NotificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class NotificacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private NotificacionLogic notificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<NotificacionEntity> data = new ArrayList<NotificacionEntity>();

    private String mensajeLargo;
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(NotificacionEntity.class.getPackage())
                .addPackage(NotificacionLogic.class.getPackage())
                .addPackage(NotificacionPersistence.class.getPackage())
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
        em.createQuery("delete from NotificacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 6; i++) {
            NotificacionEntity entity = factory.manufacturePojo(NotificacionEntity.class);
            em.persist(entity);
            data.add(entity);
            
            
        }
        
        
        
    }

    /**
     * Prueba para crear una Notificacion.
     */
    @Test
     public void createNotificacionTest() throws BusinessLogicException {
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);
        NotificacionEntity result = notificacionLogic.createNotificacion(newEntity);
        Assert.assertNotNull(result);
        NotificacionEntity entity = em.find(NotificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMensaje(), entity.getMensaje());
        Assert.assertEquals(newEntity.getTipoDeAviso(), entity.getTipoDeAviso());
        Assert.assertEquals(newEntity.getCliente(), entity.getCliente());
        Assert.assertEquals(newEntity.getEvento(), entity.getEvento());
        Assert.assertEquals(newEntity.getProveedor(), entity.getProveedor());
    }
     
        @Test
        public void UpdateNotificacionTest() throws BusinessLogicException {
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);
        NotificacionEntity anotherEntity = factory.manufacturePojo(NotificacionEntity.class);
        long idnot = anotherEntity.getId();
        NotificacionEntity result = notificacionLogic.updateNotificacion(idnot, newEntity);
        Assert.assertNotNull(result);
        NotificacionEntity entity = em.find(NotificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMensaje(), entity.getMensaje());
        Assert.assertEquals(newEntity.getTipoDeAviso(), entity.getTipoDeAviso());
        Assert.assertEquals(newEntity.getCliente(), entity.getCliente());
        Assert.assertEquals(newEntity.getEvento(), entity.getEvento());
        Assert.assertEquals(newEntity.getProveedor(), entity.getProveedor());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createNotificacionSinClienteNiProveedor() throws BusinessLogicException {
        
        String tipoda= "tipo1";
        NotificacionEntity newEntity = new NotificacionEntity();
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);        
        newEntity.setMensaje(mensajeLargo);
        newEntity.setCliente(null);
        newEntity.setProveedor(null);
        newEntity.setEvento(evento);
        newEntity.setTipoDeAviso(tipoda);
        NotificacionEntity result = notificacionLogic.createNotificacion(newEntity);
    }

        @Test(expected = BusinessLogicException.class)
    public void createNotificacionConMensajeLargo() throws BusinessLogicException {
         mensajeLargo = "mensaje";
        for (int i=0;i<300;i++)
        {
            mensajeLargo= mensajeLargo+"mensaje";
        }
        String tipoda= "tipo1";
        NotificacionEntity newEntity = new NotificacionEntity();
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);        
        newEntity.setMensaje(mensajeLargo);
        newEntity.setCliente(cliente);
        newEntity.setProveedor(proveedor);
        newEntity.setEvento(evento);
        newEntity.setTipoDeAviso(tipoda);
        NotificacionEntity result = notificacionLogic.createNotificacion(newEntity);
    }
    
    @Test
    public void updateNotificacionConMensajeLargo() throws BusinessLogicException {
         mensajeLargo = "mensaje";
        for (int i=0;i<300;i++)
        {
            mensajeLargo= mensajeLargo+"mensaje";
        }
        
        String tipoda= "tipo1";
        NotificacionEntity newEntity =  factory.manufacturePojo(NotificacionEntity.class);
        long notID = newEntity.getId();
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);        
        newEntity.setMensaje(mensajeLargo);
        newEntity.setCliente(cliente);
        newEntity.setProveedor(proveedor);
        newEntity.setEvento(evento);
        newEntity.setTipoDeAviso(tipoda);
        NotificacionEntity result = notificacionLogic.updateNotificacion(notID, newEntity);
    }
    
    @Test
    public void deleteNotificacionTest() throws BusinessLogicException {
        NotificacionEntity entity = factory.manufacturePojo(NotificacionEntity.class);
        notificacionLogic.deleteNotificacion(entity.getId());
        NotificacionEntity deleted = em.find(NotificacionEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
}