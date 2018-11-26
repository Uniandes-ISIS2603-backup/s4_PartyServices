
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.partyServices.persistence.TarjetaCreditoPersistence;
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
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoPersistenceTest {

    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos
     * aleatorios de las clases.
     */
    PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la dependencia a la clase TarjetaCreditoPersistence cuyos
     * métodos se van a probar.
     */
    @Inject
    public TarjetaCreditoPersistence tarjetaCreditoPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    public EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba para las tarjetas de credito.
     */
    private List<TarjetaCreditoEntity> data = new ArrayList<TarjetaCreditoEntity>();

    /**
     * Lista que tiene los datos de prueba para los clientes.
     */
    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();

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
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
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
        em.createQuery("Delete from TarjetaCreditoEntity").executeUpdate();
        em.createQuery("Delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            dataCliente.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);
            if (i == 0) {
                entity.setCliente(dataCliente.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una Tarjeta de Credito.
     */
    @Test
    public void createTarjetaCreditoTest() {
        PodamFactory fabrica = new PodamFactoryImpl();
        TarjetaCreditoEntity nuevaEntidad = fabrica.manufacturePojo(TarjetaCreditoEntity.class);
        TarjetaCreditoEntity result = tarjetaCreditoPersistence.create(nuevaEntidad);

        Assert.assertNotNull(result);

        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, result.getId());

        Assert.assertEquals(nuevaEntidad.getBanco(), entity.getBanco());
        Assert.assertEquals(nuevaEntidad.getCliente(), entity.getCliente());
        Assert.assertEquals(nuevaEntidad.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(nuevaEntidad.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(nuevaEntidad.getFechaExpiracion(), entity.getFechaExpiracion());
        Assert.assertEquals(nuevaEntidad.getFranquicia(), entity.getFranquicia());
        Assert.assertEquals(nuevaEntidad.getId(), entity.getId());
        Assert.assertEquals(nuevaEntidad.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(nuevaEntidad.getNumero(), entity.getNumero());
    }

    /**
     * Prueba para consultar una tarjeta de Credito
     */
    @Test
    public void getTarjetaCreditoTest() {
        TarjetaCreditoEntity entity = data.get(0);

        TarjetaCreditoEntity newEntity = tarjetaCreditoPersistence.find(entity.getCliente().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getBanco(), entity.getBanco());
        Assert.assertEquals(newEntity.getCliente(), entity.getCliente());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getFechaExpiracion(), entity.getFechaExpiracion());
        Assert.assertEquals(newEntity.getFranquicia(), entity.getFranquicia());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());
    }

    /**
     * Prueba para actualizar una Tarjeta de Credito
     */
    @Test
    public void updateTarjetaCreditoTest() {
        TarjetaCreditoEntity entity = data.get(2);
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setId(entity.getId());

        TarjetaCreditoEntity resp = tarjetaCreditoPersistence.update(newEntity);

        Assert.assertEquals(newEntity.getBanco(), resp.getBanco());
        Assert.assertEquals(newEntity.getCliente(), resp.getCliente());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), resp.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), resp.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getFechaExpiracion(), resp.getFechaExpiracion());
        Assert.assertEquals(newEntity.getFranquicia(), resp.getFranquicia());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getNombreTitular(), resp.getNombreTitular());
        Assert.assertEquals(newEntity.getNumero(), resp.getNumero());
    }

    /**
     * Prueba para eliminar una Tarjeta de Credito
     */
    @Test
    public void deleteTarjetaCreditoTest() {
        TarjetaCreditoEntity entity = data.get(1);
        tarjetaCreditoPersistence.delete(entity.getId());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
