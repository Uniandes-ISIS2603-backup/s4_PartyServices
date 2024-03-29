
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import java.util.ArrayList;
import java.util.Date;
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
public class AgendaPersistenceTest {
    
    @Inject
    private AgendaPersistence agendaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
    
    
    private List<AgendaEntity> data = new ArrayList<>();
    
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
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(AgendaPersistence.class.getPackage())
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
        em.createQuery("delete from AgendaEntity").executeUpdate();
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

            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
           
            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void crearAgendaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        AgendaEntity agendaEntity = factory.manufacturePojo(AgendaEntity.class);
        AgendaEntity resultado = agendaPersistence.create(agendaEntity);
        Assert.assertNotNull( resultado);
        AgendaEntity agendaEntityEncontrada= em.find(AgendaEntity.class,resultado.getId());
        Assert.assertEquals(agendaEntity.getFechaPenitencia().getDate(), agendaEntityEncontrada.getFechaPenitencia().getDate());
        Assert.assertEquals(agendaEntity.getFechaPenitencia().getMonth(), agendaEntityEncontrada.getFechaPenitencia().getMonth());
        Assert.assertEquals(agendaEntity.getFechaPenitencia().getYear(), agendaEntityEncontrada.getFechaPenitencia().getYear());
    }
    
    @Test
    public void deleteAgendaTest() 
    {
        AgendaEntity entidad = data.get(0);
        agendaPersistence.delete(entidad.getId());
        AgendaEntity deleted = em.find(AgendaEntity.class, entidad.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateAgendaTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
        agendaPersistence.create(entity);
        Date dia = new Date();
        entity.setFechaPenitencia(dia);
        agendaPersistence.update(entity);
        AgendaEntity agendaActualizada=agendaPersistence.find(entity.getId());
        Assert.assertEquals(agendaActualizada.getFechaPenitencia().getDay()+" "+agendaActualizada.getFechaPenitencia().getMonth()+" "+agendaActualizada.getFechaPenitencia().getYear(), 
            dia.getDay()+" "+dia.getMonth()+" "+dia.getYear());
    }
    @Test
    public void findAllTest()
    {
        Assert.assertEquals(3, agendaPersistence.findAll().size());
    }
    
}
