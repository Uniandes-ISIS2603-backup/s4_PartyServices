/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.TarjetaCreditoPersistence;
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
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoLogicTest {

    /**
     * Maquina de creación random
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la dependencia a la clase TarjetaCreditoLogic cuyos métodos
     * se van a probar.
     */
    @Inject
    private TarjetaCreditoLogic tarjetaCreditoLogic;

    /**
     * manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * transacciones de usuario
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba para las Tarjetas de Credito.
     */
    private List<TarjetaCreditoEntity> data = new ArrayList<TarjetaCreditoEntity>();

    /**
     * Lista que tiene los datos de prueba para los clientes.
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
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoLogic.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from TarjetaCreditoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
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
            entity.setCliente(dataCliente.get(i));
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
     * Prueba para crear una tarjeta de credito.
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test
    public void createTarjetaCreditoTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setCliente(dataCliente.get(1));
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("08/22");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreTitular("LAURA L");

        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity.getCliente().getId(), newEntity);

        Assert.assertNotNull(result);
        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getBanco(), entity.getBanco());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(newEntity.getFechaExpiracion(), entity.getFechaExpiracion());
        Assert.assertEquals(newEntity.getFranquicia(), entity.getFranquicia());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());

    }

    /**
     * prueba para eliminar una tarjeta de credito
     *
     * @throws BusinessLogicException Si alguna tarjeta de credito no está
     * asociada con el cliente pasado por parámetro en el método deleteCliente.
     */
    @Test
    public void deleteTarjetaCreditoTest() throws BusinessLogicException {
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaCreditoLogic.deleteTarjetaCredito(entity.getCliente().getId(), entity.getId());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * prueba para eliminar una tarjeta de credito asociada a un cliente
     *
     * @throws BusinessLogicException Si alguna tarjeta de credito no está
     * asociada con el cliente pasado por parámetro en el método deleteCliente.
     */
    @Test
    public void deleteTarjetaCreditoXClienteTest() throws BusinessLogicException {
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaCreditoLogic.deleteTarjetaCreditoXCliente(entity.getCliente().getId());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * test para obtener una tarjeta de credito.
     */
    @Test
    public void getTarjetaCreditoTest() {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity resultEntity = tarjetaCreditoLogic.getTarjetaCredito(entity.getCliente().getId(), entity.getId());

        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getBanco(), entity.getBanco());
        Assert.assertEquals(entity.getCliente(), entity.getCliente());
        Assert.assertEquals(entity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(entity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(entity.getFechaExpiracion(), entity.getFechaExpiracion());
        Assert.assertEquals(entity.getFranquicia(), entity.getFranquicia());
        Assert.assertEquals(entity.getId(), entity.getId());
        Assert.assertEquals(entity.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(entity.getNumero(), entity.getNumero());

    }

    /**
     * test para obtener una tarjeta de credito por su cliente.
     */
    @Test
    public void getTarjetaCreditoXClienteTest() {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity resultEntity = tarjetaCreditoLogic.getTarjetaCreditoByCliente(entity.getCliente().getId());

        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getBanco(), entity.getBanco());
        Assert.assertEquals(entity.getCliente(), entity.getCliente());
        Assert.assertEquals(entity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(entity.getCodigoSeguridad(), entity.getCodigoSeguridad());
        Assert.assertEquals(entity.getFechaExpiracion(), entity.getFechaExpiracion());
        Assert.assertEquals(entity.getFranquicia(), entity.getFranquicia());
        Assert.assertEquals(entity.getId(), entity.getId());
        Assert.assertEquals(entity.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(entity.getNumero(), entity.getNumero());

    }

    /**
     * prueba para actualizar una tarjeta de credito.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updateTarjetaCreditoTest() throws BusinessLogicException {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity pojoEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setCliente(entity.getCliente());
        pojoEntity.setNumero(5555555555554444L);
        pojoEntity.setFranquicia("MasterCard");
        pojoEntity.setFechaExpiracion("02/20");
        pojoEntity.setCodigoSeguridad(1523);
        pojoEntity.setNombreTitular("JESUS O");

        tarjetaCreditoLogic.updateTarjetaCredito(pojoEntity.getId(), pojoEntity);

        TarjetaCreditoEntity resp = em.find(TarjetaCreditoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getBanco(), resp.getBanco());
        Assert.assertEquals(pojoEntity.getCodigoSeguridad(), resp.getCodigoSeguridad());
        Assert.assertEquals(pojoEntity.getCodigoSeguridad(), resp.getCodigoSeguridad());
        Assert.assertEquals(pojoEntity.getFechaExpiracion(), resp.getFechaExpiracion());
        Assert.assertEquals(pojoEntity.getFranquicia(), resp.getFranquicia());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombreTitular(), resp.getNombreTitular());
        Assert.assertEquals(pojoEntity.getNumero(), resp.getNumero());

    }

    /**
     * Prueba para crear una tarjeta de credito con uno numero no valido
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoNumeroInvalidoTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setNumero(5555555555554044L);
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con un numero que no coincida
     * con la franquicia
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoNumeroInvalidoFranquiciaTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("Visa");
        newEntity.setCodigoSeguridad(123);
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

    /**
     * Prueba para crear un pago con una tarjeta de credito con una fecha de
     * expiracion que ya paso
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoFechaExpiracionVencidaTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setFechaExpiracion("12/05");
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiración
     * elevada.
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoFechaExpiracionFuturaTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setFechaExpiracion("11/50");
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

    /**
     * Prueba crear una tarjeta de credito con un codigo de seguridad invalido
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoCodigoSeguridadInvalidoTest() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setCodigoSeguridad(12);
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

    /**
     * Prueba para crear una tarjeta de credito con nombre de tarjeta invalido.
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoNombreTitularInvalido() throws BusinessLogicException, ParseException {
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        newEntity.setNombreTitular("llll<<");
        tarjetaCreditoLogic.createTarjetaCredito(dataCliente.get(2).getId(), newEntity);

    }

}
