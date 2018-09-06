
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class FechaPersistenceTest {
    @Inject
    private FechaPersistence fechaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    
    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
    
    
    private List<FechaEntity> data = new ArrayList<FechaEntity>();
    
     /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @org.jboss.arquillian.container.test.api.Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FechaEntity.class.getPackage())
                .addPackage(FechaPersistence.class.getPackage())
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from FechaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            FechaEntity entity = factory.manufacturePojo(FechaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void crearFechaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        FechaEntity fechaEntity = factory.manufacturePojo(FechaEntity.class);
        FechaEntity resultado = fechaPersistence.create(fechaEntity);
        Assert.assertNotNull( resultado);
        FechaEntity fechaEntityEncontrada= em.find(FechaEntity.class,resultado.getId());
        Assert.assertEquals(fechaEntity.getDia(), fechaEntityEncontrada.getDia());
    }
    
    @Test
    public void deleteFechaTest() {
        FechaEntity entidad = data.get(0);
        fechaPersistence.delete(entidad.getId());
        FechaEntity deleted = em.find(FechaEntity.class, entidad.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void findFechaByDiaTest() {
        FechaEntity entity = data.get(0);
        String dia ="";
        
        FechaEntity newEntity = fechaPersistence.findByDia(dia); 
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
}
