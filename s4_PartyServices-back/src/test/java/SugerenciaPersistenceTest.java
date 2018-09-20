/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
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
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class) 
public class SugerenciaPersistenceTest {
    
    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
     */
    PodamFactory factory;
    
    /**
     * Inyección de la dependencia a la clase SugerenciaPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private SugerenciaPersistence sugerenciaPersistence;
    
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
     * Lista que tiene los datos de prueba para las sugerencias.
     */
    private List<SugerenciaEntity> data = new ArrayList<SugerenciaEntity>();
    
    /**
     * Lista que tiene los datos de prueba para las tematicas.
     */
    private List<TematicaEntity> dataTematica = new ArrayList<TematicaEntity>();
    
    
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Sugerencia, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SugerenciaEntity.class.getPackage())
                .addPackage(SugerenciaPersistence.class.getPackage())
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
        em.createQuery("delete from SugerenciaEntity").executeUpdate();
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         factory = new PodamFactoryImpl();
        
        for (int i = 0; i < 3; i++) {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            em.persist(entity);
            dataTematica.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {

            SugerenciaEntity entity = factory.manufacturePojo(SugerenciaEntity.class);
            if (i == 0) {
                entity.setTematica(dataTematica.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una Sugerencia.
     */
    @Test
    public void createSugerenciaTest() {
     
        SugerenciaEntity newEntity = factory.manufacturePojo(SugerenciaEntity.class);
        SugerenciaEntity result = sugerenciaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SugerenciaEntity entity = em.find(SugerenciaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
    }
    
      /**
     * Prueba para consultar una Sugerencia.
     */
    @Test
    public void getSugerenciaTest() {
        SugerenciaEntity entity = data.get(0);
        SugerenciaEntity newEntity = sugerenciaPersistence.find(dataTematica.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
        Assert.assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para eliminar una Sugerencia.
     */
    @Test
    public void deleteSugerenciaTest() {
        SugerenciaEntity entity = data.get(0);
        sugerenciaPersistence.delete(entity.getId());
        SugerenciaEntity deleted = em.find(SugerenciaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar una Sugerencia.
     */
    @Test
    public void updateSugerenciaTest() {
        SugerenciaEntity entity = data.get(0);
        SugerenciaEntity newEntity = factory.manufacturePojo(SugerenciaEntity.class);

        newEntity.setId(entity.getId());

        sugerenciaPersistence.update(newEntity);

        SugerenciaEntity resp = em.find(SugerenciaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(newEntity.getNombreUsuario(), resp.getNombreUsuario());
    }
    
}