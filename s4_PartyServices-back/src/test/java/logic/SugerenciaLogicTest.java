/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.SugerenciaLogic;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
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
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class)
public class SugerenciaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Inyección de la dependencia a la clase ValoracionLogic cuyos métodos se
     * van a probar.
     */
    @Inject
    private SugerenciaLogic sugerenciaLogic;
    
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
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SugerenciaEntity.class.getPackage())
                .addPackage(SugerenciaLogic.class.getPackage())
                .addPackage(SugerenciaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Lista que tiene los datos de prueba.
     */
    private List<SugerenciaEntity> data = new ArrayList<SugerenciaEntity>();
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from SugerenciaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SugerenciaEntity entity = factory.manufacturePojo(SugerenciaEntity.class);

            em.persist(entity);
            data.add(entity);

        }
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
     * Prueba para crear una Sugerencia.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createSugerenciaTest() throws BusinessLogicException {
        SugerenciaEntity newEntity = factory.manufacturePojo(SugerenciaEntity.class);
        SugerenciaEntity result = sugerenciaLogic.createSugerencia(newEntity);
        Assert.assertNotNull(result);
        
        SugerenciaEntity entity = em.find(SugerenciaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }
    
    /**
     * Prueba para crear una Sugerencia con más caracteres que el límite.
     * 
     * @throws BusinessLogicException
     */
    @Test (expected= BusinessLogicException.class)
    public void createSugerenciaLimiteCaracteresTest() throws BusinessLogicException {
        String mensajeGrande = "hola";
        for(int i=0; i<5001; i++){
            mensajeGrande = mensajeGrande.concat("hola");
        }
        SugerenciaEntity newEntity = new SugerenciaEntity();
        newEntity.setComentario(mensajeGrande);
        SugerenciaEntity result = sugerenciaLogic.createSugerencia(newEntity);
    }
    
    /**
     * Prueba para eliminar una Sugerencia.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteSugerenciaTest() throws BusinessLogicException {
        SugerenciaEntity entity = data.get(1);
        sugerenciaLogic.deleteSugerencia(entity.getId());
        SugerenciaEntity deleted = em.find(SugerenciaEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
    
}
