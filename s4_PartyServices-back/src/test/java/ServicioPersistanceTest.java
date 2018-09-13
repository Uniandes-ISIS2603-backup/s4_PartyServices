

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.persistence.ServicioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 * Prueba de persistencia de Servicio
 *
 * @author Tomas Vargas
 */

@RunWith(Arquillian.class)
public class ServicioPersistanceTest {
    
    /**
     * Inyección de la dependencia a la clase ServicioPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ServicioPersistence servicioPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * lista que tiene los datos de prueba.
     */
    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Servicio, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
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
            em.joinTransaction();
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
        em.createQuery("delete from ServicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una Servicio.
     */
    @Test
    public void createServicioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        ServicioEntity result = servicioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
    }

    /**
     * Prueba para consultar la lista de Servicios.
     */
    @Test
    public void getServiciosTest() {
        List<ServicioEntity> list = servicioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity ent : list) {
            boolean found = false;
            for (ServicioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Servicio.
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity newEntity = servicioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTipo(), newEntity.getTipo());
    }

    /**
     * Prueba para eliminar una Servicio.
     */
    @Test
    public void deleteServicioTest() {
        ServicioEntity entity = data.get(0);
        servicioPersistence.delete(entity.getId());
        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Servicio.
     */
    @Test
    public void updateServicioTest() {
        ServicioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);

        newEntity.setId(entity.getId());

        servicioPersistence.update(newEntity);

        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTipo(), resp.getTipo());
    }
    
}
