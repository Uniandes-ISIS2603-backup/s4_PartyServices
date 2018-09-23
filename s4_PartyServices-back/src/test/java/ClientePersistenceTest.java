
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Pruebas de persistencia de cliente
 *
 * @author Elias Negrete
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest {

    /**
     * Inyección de la persistencia de cliente para acceder a sus métodos.
     */
    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Entity Manager que regula la persistencia.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Transacciones de usuario
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista de entidades-cliente
     */
    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            //creación random de clientes-entidades
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            //persistencia de lo anterior
            em.persist(entity);

            //añadir a lista para usar
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Cliente.
     */
    @Test
    public void createClienteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(newEntity);

        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());

        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(newEntity.getEmail(), entity.getEmail());
        Assert.assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
    }

    /**
     * Prueba para consultar la lista de Clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clientePersistence.findAll();
        int cantidad = list.size();
        Assert.assertEquals(data.size(), cantidad);
        for (ClienteEntity entidadCliente : list) {
            boolean encontrado = false;
            for (ClienteEntity entityCliente : data) {
                if (entidadCliente.getId().equals(entityCliente.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para consultar un cliente.
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entidadCliente = data.get(0);
        ClienteEntity entidadNuevaCliente = clientePersistence.find(entidadCliente.getId());
        Assert.assertEquals(entidadNuevaCliente.getLogin(), entidadCliente.getLogin());
        Assert.assertEquals(entidadNuevaCliente.getContrasenia(), entidadCliente.getContrasenia());
        Assert.assertEquals(entidadNuevaCliente.getEmail(), entidadCliente.getEmail());
        Assert.assertEquals(entidadNuevaCliente.getFechaNacimiento(), entidadCliente.getFechaNacimiento());

    }

    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entidadNueva = data.get(0);
        clientePersistence.delete(entidadNueva.getId());
        ClienteEntity eliminado = em.find(ClienteEntity.class, entidadNueva.getId());
        Assert.assertNull(eliminado);

    }

    @Test
    public void FindClienteByLoginTest() {
        ClienteEntity entidad = data.get(0);
        ClienteEntity nuevaEntidad = clientePersistence.findByLogin(entidad.getLogin());
        Assert.assertNotNull(nuevaEntidad);
        Assert.assertEquals(entidad.getLogin(), nuevaEntidad.getLogin());

        nuevaEntidad = clientePersistence.findByLogin(null);
        Assert.assertNull(nuevaEntidad);
    }

    /**
     * Prueba para actualizar un administrador.
     */

    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity nuevaEntidad = factory.manufacturePojo(ClienteEntity.class);
        nuevaEntidad.setId(entity.getId());
        clientePersistence.update(nuevaEntidad);
        ClienteEntity encontrado = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(nuevaEntidad.getId(), encontrado.getId());
    }

}
