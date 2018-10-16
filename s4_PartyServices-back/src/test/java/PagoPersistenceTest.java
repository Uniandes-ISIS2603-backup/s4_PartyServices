
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
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
 * Pruebas de la persistencia de la clase Pago
 *
 * @author Elias Negrete
 */
@RunWith(Arquillian.class)
public class PagoPersistenceTest {

    /**
     * Inyección de la persistencia de cliente para acceder a sus métodos.
     */
    @Inject
    private PagoPersistence pagoPersistence;

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
     * Lista de entidades-pago
     */
    private List<PagoEntity> data = new ArrayList<PagoEntity>();

    /**
     * Lista de entidades-cliente
     */
    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * La forma como se configura la prueba.
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
     * Limpia las tablas de los datos.
     */
    private void clearData() {
        em.createQuery("delete from PagoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();

    }

    /**
     * Inserta los archivos de inicio para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            em.persist(entity);

            dataCliente.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
            if (i == 0) {
                entity.setClienteEntity(dataCliente.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un pago
     */
    @Test
    public void createPagoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PagoEntity nuevaEntidad = factory.manufacturePojo(PagoEntity.class);
        PagoEntity resultadoEntity = pagoPersistence.create(nuevaEntidad);

        Assert.assertNotNull(resultadoEntity);

        PagoEntity entity = em.find(PagoEntity.class, resultadoEntity.getId());

        Assert.assertEquals(nuevaEntidad.getUsuarioEntity(), entity.getUsuarioEntity());
        Assert.assertEquals(nuevaEntidad.getCodigoSeguridadTarjetaEntity(), entity.getCodigoSeguridadTarjetaEntity());
        Assert.assertEquals(nuevaEntidad.getEmpresaEntity(), entity.getEmpresaEntity());
        Assert.assertEquals(nuevaEntidad.getFechaExpiracionTarjetaCreditoEntity(), entity.getFechaExpiracionTarjetaCreditoEntity());

    }

    /**
     * Prueba para consultar un Pago.
     */
    @Test
    public void getPagoTest() {
        PagoEntity entidad = data.get(0);
        PagoEntity nuevaEntidad = pagoPersistence.find(dataCliente.get(0).getId(), entidad.getId());
        Assert.assertNotNull(nuevaEntidad);
        Assert.assertEquals(entidad.getEmpresaEntity(), nuevaEntidad.getEmpresaEntity());
        Assert.assertEquals(entidad.getNombreTarjetaEntity(), nuevaEntidad.getNombreTarjetaEntity());
        Assert.assertEquals(entidad.getNumeroTarjetaCreditoEntity(), nuevaEntidad.getNumeroTarjetaCreditoEntity());
        Assert.assertEquals(entidad.getUsuarioEntity(), nuevaEntidad.getUsuarioEntity());
        Assert.assertEquals(entidad.getCodigoSeguridadTarjetaEntity(), nuevaEntidad.getCodigoSeguridadTarjetaEntity());
        Assert.assertEquals(entidad.getFechaExpiracionTarjetaCreditoEntity(), nuevaEntidad.getFechaExpiracionTarjetaCreditoEntity());
    }

    /**
     * Prueba para eliminar un pago
     */
    @Test
    public void deletePagoTest() {
        PagoEntity entity = data.get(0);
        pagoPersistence.delete(entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());
        Assert.assertNull(deleted);

    }

    /**
     * Prueba para consultar la lista de pagos.
     */
    @Test
    public void getPagosTest() {
        List<PagoEntity> list = pagoPersistence.findAll();
        int cantidad = list.size();
        Assert.assertEquals(data.size(), cantidad);
        for (PagoEntity entidadPago : list) {
            boolean encontrado = false;
            for (PagoEntity entidadNuevaPago : data) {
                if (entidadPago.getId().equals(entidadNuevaPago.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para actualizar un pago.
     */
    @Test
    public void updatePagoTest() {
        PagoEntity entidadPago = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PagoEntity nuevaEntidadPago = factory.manufacturePojo(PagoEntity.class);

        nuevaEntidadPago.setId(entidadPago.getId());

        pagoPersistence.update(nuevaEntidadPago);

        PagoEntity resp = em.find(PagoEntity.class, entidadPago.getId());

        Assert.assertEquals(nuevaEntidadPago.getEmpresaEntity(), resp.getEmpresaEntity());
        Assert.assertEquals(nuevaEntidadPago.getNombreTarjetaEntity(), resp.getNombreTarjetaEntity());
        Assert.assertEquals(nuevaEntidadPago.getNumeroTarjetaCreditoEntity(), resp.getNumeroTarjetaCreditoEntity());
        Assert.assertEquals(nuevaEntidadPago.getUsuarioEntity(), resp.getUsuarioEntity());
        Assert.assertEquals(nuevaEntidadPago.getCodigoSeguridadTarjetaEntity(), resp.getCodigoSeguridadTarjetaEntity());
        Assert.assertEquals(nuevaEntidadPago.getFechaExpiracionTarjetaCreditoEntity(), resp.getFechaExpiracionTarjetaCreditoEntity());

    }

    /**
     * Prueba para encontrar un pago por el login de su cliente
     */
    @Test
    public void FindPagoByUsuarioTest() {
        PagoEntity entity = data.get(0);
        PagoEntity newEntity = pagoPersistence.findByUsuario(entity.getUsuarioEntity());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUsuarioEntity(), newEntity.getUsuarioEntity());
    }

}
