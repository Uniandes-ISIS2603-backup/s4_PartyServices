/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.SugerenciaLogic;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
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
    
        /**
         * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
         */
        private PodamFactory factory;
    
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
     * Lista que tiene los datos de prueba para las sugerencias.
     */
    private List<SugerenciaEntity> data = new ArrayList<SugerenciaEntity>();
    
    /**
     * Lista que tiene los datos de prueba para crear una temática con muchas sugerencias.
     */
    private List<SugerenciaEntity> dataMuchasSugerencias = new ArrayList<SugerenciaEntity>();
    
    /**
     * Lista que tiene los datos de prueba para las tematicas.
     */
    private List<TematicaEntity> dataTematica = new ArrayList<TematicaEntity>();
    
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
        em.createQuery("delete from SugerenciaEntity").executeUpdate();
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        factory  = new PodamFactoryImpl();
        
        for (int i = 0; i < 2; i++) {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            em.persist(entity);
            dataTematica.add(entity);
        }
        
        for (int i = 0; i < 2; i++) {
            SugerenciaEntity entity = factory.manufacturePojo(SugerenciaEntity.class);
            entity.setTematica(dataTematica.get(i));
            em.persist(entity);
            data.add(entity);
        }
        
        TematicaEntity tematicaEntityListaSugerencias = factory.manufacturePojo(TematicaEntity.class);
        em.persist(tematicaEntityListaSugerencias);
        
        for (int i = 0; i < 10; i++) {
            SugerenciaEntity entity = factory.manufacturePojo(SugerenciaEntity.class);
            entity.setTematica(tematicaEntityListaSugerencias);
            em.persist(entity);
            dataMuchasSugerencias.add(entity);
        }
      
        dataTematica.add(tematicaEntityListaSugerencias); //dataTematica(2) = tematicaEntityListaSugerencias
    }
    
    /**
     * Prueba para crear una Sugerencia.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createSugerenciaTest() throws BusinessLogicException {
        SugerenciaEntity newEntity = factory.manufacturePojo(SugerenciaEntity.class);
        newEntity.setTematica(dataTematica.get(2));
        newEntity.setLink("https://algooooooooo");
        SugerenciaEntity result = sugerenciaLogic.createSugerencia(newEntity.getTematica().getId(), newEntity);
        Assert.assertNotNull(result);
        
        SugerenciaEntity entity = em.find(SugerenciaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getTematica(), entity.getTematica());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
        
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
        newEntity.setTematica(dataTematica.get(0));
        SugerenciaEntity result = sugerenciaLogic.createSugerencia(newEntity.getTematica().getId(), newEntity);
    }
    
    /**
     * Prueba para consultar la lista de sugerencias.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getSugerenciasTest() throws BusinessLogicException {
        List<SugerenciaEntity> list = sugerenciaLogic.getSugerencias(dataTematica.get(2).getId());
        Assert.assertEquals(dataMuchasSugerencias.size(), list.size());
        for (SugerenciaEntity entity : list) {
            boolean found = false;
            for (SugerenciaEntity storedEntity : dataMuchasSugerencias) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una Sugerencia.
     */
    @Test
    public void getSugerenciaTest() {
        SugerenciaEntity entity = data.get(0);
        SugerenciaEntity resultEntity = sugerenciaLogic.getSugerencia(dataTematica.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getNombreUsuario(), resultEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para actualizar una Sugerencia.
     * @throws BusinessLogicException
     */
    @Test
    public void updateSugerenciaTest() throws BusinessLogicException{
        SugerenciaEntity entity = data.get(1);
        SugerenciaEntity pojoEntity = factory.manufacturePojo(SugerenciaEntity.class);

        pojoEntity.setId(entity.getId());

        sugerenciaLogic.updateSugerencia(dataTematica.get(1).getId(), pojoEntity);

        SugerenciaEntity resp = em.find(SugerenciaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(pojoEntity.getNombreUsuario(), resp.getNombreUsuario());
    }
    
    /**
     * Prueba para actualizar una Sugerencia con más caracteres que el límite.
     * 
     * @throws BusinessLogicException
     */
    @Test (expected= BusinessLogicException.class)
    public void updateSugerenciaLimiteCaracteresTest() throws BusinessLogicException {
        String mensajeGrande = "hola";
        for(int i=0; i<5001; i++){
            mensajeGrande = mensajeGrande.concat("hola");
        }
        SugerenciaEntity newEntity = new SugerenciaEntity();
        newEntity.setComentario(mensajeGrande);
        newEntity.setTematica(dataTematica.get(0));
        SugerenciaEntity result = sugerenciaLogic.updateSugerencia(newEntity.getTematica().getId(), newEntity);
    }
   
    /**
     * Prueba para eliminar una Sugerencia.
     *
     * @throws BusinessLogicException Si alguna sugerencia no está asociada con la temática pasada por parámetro en el método deleteSugerencia.
     */
    @Test
    public void deleteSugerenciaTest() throws BusinessLogicException {
        SugerenciaEntity entity = data.get(1);
        sugerenciaLogic.deleteSugerencia(dataTematica.get(1).getId(), entity.getId());
        SugerenciaEntity deleted = em.find(SugerenciaEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
    
    /**
     * Prueba para eliminarle una sugerencia a una temática de la cual no pertenece.
     *
     * @throws BusinessLogicException Si alguna sugerencia no está asociada con la temática pasada por parámetro en el método deleteSugerencia.
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteSugerenciaConTematicaNoAsociadaTest() throws BusinessLogicException {
        SugerenciaEntity entity = data.get(0);
        sugerenciaLogic.deleteSugerencia(dataTematica.get(1).getId(), entity.getId());
    }
    
}
