/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ClienteLogic;
import co.edu.uniandes.csw.partyServices.ejb.PagoLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import java.text.ParseException;
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
public class PagoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagoLogic PagoLogic;
    @Inject
    private ClienteLogic clienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoLogic.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
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
        em.createQuery("delete from PagoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
            em.persist(entity);
            data.add(entity);
        }

    }

    /**
     * Prueba para crear una tarjeta de credito
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test
    public void crearPagoTest() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setNumeroTarjetaCredito(5555555555554444L);
        newEntity.setEmpresa("MasterCard");
        newEntity.setFechaExpiracionTarjetaCredito("11/21");
        newEntity.setCodigoSeguridadTarjeta(123);
        newEntity.setNombreTarjeta("LAURA L");

        ClienteEntity newEntity2 = factory.manufacturePojo(ClienteEntity.class);

        newEntity2.setId((long) 1);
        newEntity2.setFechaNacimiento("21/10/1997");
        newEntity2.setEmail("aaaaaaaa@udad.com");
        newEntity2.setLogin("lololololololo");
        newEntity2.setContrasenia("aaaaaaaa");

        newEntity2.setId(1l);
        Assert.assertNotNull(clienteLogic.createCliente(newEntity2));
        System.out.println(newEntity2.getId());
        PagoEntity result = PagoLogic.createPago(newEntity2.getId(), newEntity);
        Assert.assertNotNull(result);

        PagoEntity entity = em.find(PagoEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroTarjetaCredito(), newEntity.getNumeroTarjetaCredito());
    }

    /**
     * prueba para eliminar una tarjeta
     */
    @Test
    public void deletePagoTest() throws BusinessLogicException {
        PagoEntity entity = data.get(0);
        PagoLogic.deletePago(1l ,entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());

    }

    /**
     * test para obtener una tarjeta de credito
     */
    @Test
    public void getPagoTest() {
        PagoEntity ent = data.get(0);
        PagoEntity result = PagoLogic.getPago(1l, ent.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(ent.getFechaExpiracionTarjetaCredito(), result.getFechaExpiracionTarjetaCredito());
        Assert.assertEquals(ent.getCodigoSeguridadTarjeta(), result.getCodigoSeguridadTarjeta());
        Assert.assertEquals(ent.getEmpresa(), result.getEmpresa());
        Assert.assertEquals(ent.getNombreTarjeta(), result.getNombreTarjeta());
    }

    /**
     * Prueba para crear una tarjeta de credito con un numero no valido
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConNumeroNoValido() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setNumeroTarjetaCredito(5555555555554044L);
        PagoLogic.createPago(1, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con un numero que no coincida
     * con la franquicia
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConNumeroQueNoCoincidaConLaFranquicia() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setNumeroTarjetaCredito(5555555555554444L);
        newEntity.setEmpresa("Visa");
        newEntity.setCodigoSeguridadTarjeta(123);
        PagoLogic.createPago(1l, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion que
     * ya paso
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConFechaExpiracionPasada() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setFechaExpiracionTarjetaCredito("11/08");
        PagoLogic.createPago(1l, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion 20
     * anos mayor
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConFechaExpiracionFutura() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setFechaExpiracionTarjetaCredito("11/50");
        PagoLogic.createPago(1l, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion que
     * no cumpla el formato
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConFechaExpiracionNoCumpleFormato() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setFechaExpiracionTarjetaCredito("1150");

        PagoLogic.createPago(1l, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con un codigo de seguridad
     * invalido
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConCodigoInvalido() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setCodigoSeguridadTarjeta(12);
        PagoLogic.createPago(1l, newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con un nombre en la tarjeta
     * invalido
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearPagoTestConNombreInvalido() throws BusinessLogicException, ParseException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setNombreTarjeta("llll<<");
        PagoLogic.createPago(1l, newEntity);

    }

}
