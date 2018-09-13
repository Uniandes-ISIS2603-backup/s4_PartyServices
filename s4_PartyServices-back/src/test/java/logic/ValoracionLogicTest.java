/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ValoracionLogic;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
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
public class ValoracionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Inyección de la dependencia a la clase ValoracionLogic cuyos métodos se
     * van a probar.
     */
    @Inject
    private ValoracionLogic valoracionLogic;

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
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionLogic.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Lista que tiene los datos de prueba.
     */
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ValoracionEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);

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
     * Prueba para crear una Valoracion.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createValoracionTest() throws BusinessLogicException {
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);
        ValoracionEntity result = valoracionLogic.createValoracion(newEntity);
        Assert.assertNotNull(result);
        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertEquals(newEntity.getPuntaje(), entity.getPuntaje());
    }
    
    /**
     * Prueba para crear una Valoracion con más caracteres que el límite.
     * 
     * @throws BusinessLogicException
     */
    @Test (expected= BusinessLogicException.class)
    public void createValoracionLimiteCaracteresTest() throws BusinessLogicException {
        String mensajeGrande = "hola";
        for(int i=0; i<3000; i++){
            mensajeGrande = mensajeGrande.concat("hola");
        }
        ValoracionEntity newEntity = new ValoracionEntity();
        newEntity.setComentario(mensajeGrande);
        newEntity.setPuntaje(5);
        ValoracionEntity result = valoracionLogic.createValoracion(newEntity);
    }
    
    /**
     * Prueba para eliminar una Valoracion.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteValoracionTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(1);
        valoracionLogic.deleteValoracion(entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
    
}
