

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
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
 * Pruebas de persistencia de Proveedor
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class ProveedorPersistenceTest {

    /**
     * Inyección de la dependencia a la clase ProveedorPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;

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
     * Lista que tiene los datos de prueba.
     */
    private List<ProveedorEntity> data = new ArrayList<ProveedorEntity>();

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
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
        em.createQuery("delete from ProveedorEntity").executeUpdate();
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

            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear un proveedor.
     *
     *
     */
    @Test
    public void createProveedorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = proveedorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ProveedorEntity entity = em.find(ProveedorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para eliminar un proveedor.
     *
     *
     */
    @Test
    public void deleteProveedorTest() {
        ProveedorEntity entity = data.get(0);
        proveedorPersistence.delete(entity.getId());
        ProveedorEntity deleted = em.find(ProveedorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para consultar un proveedor por nombre.
     *
     *
     */
    @Test
    public void FindProveedorByNameTest() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = proveedorPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
     @Test
    public void validateTest() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = proveedorPersistence.validate(entity.getNombre(), entity.getContrasenia());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
}