/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ClienteLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.util.ConstantesEvento;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.validation.constraints.AssertFalse;
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
 * Pruebas de la lógica de Cliente
 *
 * @author Elias Negrete
 */
@RunWith(Arquillian.class)
public class ClienteLogicTest {

    /**
     * Maquina de creación random
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * inserción de una instancia de la logica de Clientes
     */
    @Inject
    private ClienteLogic ClienteLogic;

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
     * Lista de los clientes a probar
     */
    private List<ClienteEntity> data = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
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
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Cliente.
     *
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin("ultraspidermandark");
        newEntity.setEmail("minecraftero@dosmasdos.com");
        newEntity.setContrasenia("eeeeeeee");
        newEntity.setFechaNacimiento("05/12/1999");

        ClienteEntity result = ClienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        Assert.assertEquals(newEntity.getEmail(), entity.getEmail());

    }

    /**
     * prueba para eliminar un Cliente
     *
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void deleteClienteTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ClienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleteado = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleteado);
    }

    /**
     * prueba para eliminar un Cliente con eventos en progreso
     *
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteClienteTest2() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = ClienteLogic.getCliente(entity.getId());

        EventoEntity entityEvento = factory.manufacturePojo(EventoEntity.class);
        entityEvento.setEstado(ConstantesEvento.EN_PROCESO);
        entityEvento.setCliente(newEntity);
        List<EventoEntity> lista = new ArrayList<>();
        lista.add(entityEvento);
        newEntity.setEventos(lista);

        newEntity.setLogin("ultraspidermandark");
        newEntity.setEmail("minecraftero@dosmasdos.com");
        newEntity.setContrasenia("eeeeeeee");
        newEntity.setFechaNacimiento("05/12/1999");

        ClienteLogic.updateCliente(entity.getId(), newEntity);

        ClienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleteado = em.find(ClienteEntity.class, entity.getId());

        Assert.assertNull(deleteado);

    }

    /**
     * Prueba para crear un Cliente con un login repetido
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void loginRepetidoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin(data.get(0).getLogin());

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * prueba para probar crear un Cliente con una fecha de nacimiento superior
     * a la actual
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void fechaSuperiorTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("10/10/2112");

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * prueba para probar crear un Cliente con una edad inferior a la requerida
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void edadRequeridaTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("07/07/2017");

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * prueba para actualizar a un cliente
     *
     * @throws BusinessLogicException
     */
    public void updateClienteTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ClienteEntity pojoEntity = factory.manufacturePojo(ClienteEntity.class);
        pojoEntity.setId(entity.getId());
        ClienteLogic.updateCliente(pojoEntity.getId(), pojoEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getContrasenia(), resp.getContrasenia());
        Assert.assertEquals(pojoEntity.getEmail(), resp.getEmail());
        Assert.assertEquals(pojoEntity.getFechaNacimiento(), resp.getFechaNacimiento());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());

    }

    /**
     * Prueba de crear una fecha con formato incorrecto
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void fechaFormatoIncorrectoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("0130/2008");

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba de crear una fecha invalida
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void fechaInvalidaTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento(null);

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba de crear un login con formato incorrecto
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void loginFormatoIncorrectoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin("");
        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba de crear un login invalido
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void loginInvalidoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin(null);
        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba de crear una contrasenia con formato incorrecto.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void contraseniaFormatoIncorrectoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasenia("++++");

        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba de validaciones
     *
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */

    @Test(expected = BusinessLogicException.class)
    public void validacionesFalseTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        ClienteLogic.createCliente(newEntity);
        Assert.assertFalse(ClienteLogic.validaciones(newEntity));
    }

    /**
     * Prueba para crear un Cliente con un mail que no cumple el formato
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void mailFormatoIncorrectoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setEmail("lolololo@com");
        ClienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba para consultar la lista de clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = ClienteLogic.getClientes();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un cliente.
     */
    @Test
    public void getBookTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = ClienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getContrasenia(), resultEntity.getContrasenia());
        Assert.assertEquals(entity.getEmail(), resultEntity.getEmail());
        Assert.assertEquals(entity.getFechaNacimiento(), resultEntity.getFechaNacimiento());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
    }

}
