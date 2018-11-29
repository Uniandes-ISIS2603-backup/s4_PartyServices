/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.ServicioLogic;
import co.edu.uniandes.csw.partyServices.ejb.ServicioProveedoresLogic;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ServicioPersistence;
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
public class ServicioProveedoresLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;
    @Inject
    private ServicioProveedoresLogic servicioProveedoresLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    private List<ProveedorEntity> proveedoresData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
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
        em.createQuery("delete from ServicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedores = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(proveedores);
            proveedoresData.add(proveedores);
        }
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                proveedoresData.get(i).setServicio(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Proveedores existente a un Servicio.
     */
    @Test
    public void addProveedoresTest() {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedoresData.get(1);
        ProveedorEntity response = servicioProveedoresLogic.addProveedor(proveedorEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(proveedorEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Proveedores asociadas a una
     * instancia Servicio.
     */
    @Test
    public void getProveedoresTest() {
        List<ProveedorEntity> list = servicioProveedoresLogic.getProveedores(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Proveedores asociada a una instancia
     * Servicio.
     *
     * @throws co.edu.uniandes.csw.proveedorestore.exceptions.BusinessLogicException
     */
    @Test
    public void getProveedorTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedoresData.get(0);
        ProveedorEntity response = servicioProveedoresLogic.getProveedor(entity.getId(), proveedorEntity.getId());

        Assert.assertEquals(proveedorEntity.getId(), response.getId());
        Assert.assertEquals(proveedorEntity.getNombre(), response.getNombre());
        Assert.assertEquals(proveedorEntity.getContrasenia(), response.getContrasenia());
        Assert.assertEquals(proveedorEntity.getCalificacion(), response.getCalificacion());
    }

    /**
     * Prueba para obtener una instancia de Proveedores asociada a una instancia
     * Servicio que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.proveedorestore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getProveedorNoAsociadoTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedoresData.get(1);
        servicioProveedoresLogic.getProveedor(entity.getId(), proveedorEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Proveedores asociadas a una instancia
     * de Servicio.
     */
    @Test
    public void replaceProveedoresTest() {
        ServicioEntity entity = data.get(0);
        List<ProveedorEntity> list = proveedoresData.subList(1, 3);
        servicioProveedoresLogic.replaceProveedores(entity.getId(), list);

        entity = servicioLogic.getServicio(entity.getId());
        Assert.assertFalse(entity.getProveedores().contains(proveedoresData.get(0)));
        Assert.assertTrue(entity.getProveedores().contains(proveedoresData.get(2)));
    }
}
