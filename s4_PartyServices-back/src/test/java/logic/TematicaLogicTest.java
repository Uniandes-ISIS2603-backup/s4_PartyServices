/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.TematicaLogic;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.TematicaPersistence;
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
 * @author Tomas Vargas
 */

@RunWith(Arquillian.class)
public class TematicaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TematicaLogic tematicaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TematicaEntity> data = new ArrayList<TematicaEntity>();

    private List<ServicioEntity> serviciosData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TematicaEntity.class.getPackage())
                .addPackage(TematicaLogic.class.getPackage())
                .addPackage(TematicaPersistence.class.getPackage())
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
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicios = factory.manufacturePojo(ServicioEntity.class);
            em.persist(servicios);
            serviciosData.add(servicios);
        }
        for (int i = 0; i < 3; i++) {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                serviciosData.get(i).setTematica(entity);
            }
        }
    }

    /**
     * Prueba para crear un Tematica.
     *
     * @throws co.edu.uniandes.csw.serviciostore.exceptions.BusinessLogicException
     */
    @Test
    public void createTematicaTest() throws BusinessLogicException {
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);
        TematicaEntity result = tematicaLogic.createTematica(newEntity);
        Assert.assertNotNull(result);
        TematicaEntity entity = em.find(TematicaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para crear un Tematica con el mismo nombre de un Tematica que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.serviciostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTematicaConMismoNombreTest() throws BusinessLogicException {
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);
        newEntity.setName(data.get(0).getName());
        tematicaLogic.createTematica(newEntity);
    }

    /**
     * Prueba para consultar la lista de Tematicas.
     */
    @Test
    public void getTematicasTest() {
        List<TematicaEntity> list = tematicaLogic.getTematicas();
        Assert.assertEquals(data.size(), list.size());
        for (TematicaEntity entity : list) {
            boolean found = false;
            for (TematicaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Tematica.
     */
    @Test
    public void getTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity resultEntity = tematicaLogic.getTematica(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para actualizar un Tematica.
     */
    @Test
    public void updateTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity pojoEntity = factory.manufacturePojo(TematicaEntity.class);
        pojoEntity.setId(entity.getId());
        tematicaLogic.updateTematica(pojoEntity.getId(), pojoEntity);
        TematicaEntity resp = em.find(TematicaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

    /**
     * Prueba para eliminar un Tematica.
     *
     * @throws co.edu.uniandes.csw.serviciostore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteTematicaTest() throws BusinessLogicException {
        TematicaEntity entity = data.get(1);
        tematicaLogic.deleteTematica(entity.getId());
        TematicaEntity deleted = em.find(TematicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar una Tematica con servicios asociados.
     *
     * @throws co.edu.uniandes.csw.serviciostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteTematicaConServiciosAsociadosTest() throws BusinessLogicException {
        TematicaEntity entity = data.get(0);
        tematicaLogic.deleteTematica(entity.getId());
    }

}
