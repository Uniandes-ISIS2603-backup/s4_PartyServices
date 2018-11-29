/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
 * Pruebas de logica de Pago
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class)
public class PagoLogicTest {

    /**
     * Maquina de creación random
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * inserción de una instancia de la logica de Pago
     */
    @Inject
    private PagoLogic pagoLogic;


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
     * Lista de los pagos a probar
     */
    private List<PagoEntity> data = new ArrayList<>();

    /**
     * Lista de los clientes a probar
     */
    private List<ClienteEntity> dataCliente = new ArrayList();

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
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
            entity.setCliente(dataCliente.get(1));

            em.persist(entity);
            data.add(entity);
        }

    }

    /**
     * Prueba para crear un pago
     *
     * @throws BusinessLogicException
     * @throws java.text.ParseException
     */
    @Test
    public void crearPagoTest() throws BusinessLogicException, ParseException {
       PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setCliente(dataCliente.get(1));
        newEntity.setFecha("10/11/2018");

        if(newEntity.getValor() <0){
            newEntity.setValor(newEntity.getValor()*-1);
        }
        PagoEntity result = pagoLogic.createPago(newEntity.getCliente().getId(), newEntity);

        Assert.assertNotNull(result);
        PagoEntity entity = em.find(PagoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getValor(), entity.getValor());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());

    }

    /**
     * Prueba para consultar la lista de pagos.
     *
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void getPagosTest() throws BusinessLogicException {
        List<PagoEntity> list = pagoLogic.getPagos(dataCliente.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (PagoEntity entity : list) {
            boolean found = false;
            for (PagoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * prueba para eliminar un pago
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void deletePagoTest() throws BusinessLogicException {
        PagoEntity entity = data.get(0);
        pagoLogic.deletePago(dataCliente.get(1).getId(), entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }

    /**
     * test para obtener un pago
     */
    @Test
    public void getPagoTest() {
        PagoEntity entity = data.get(0);
        PagoEntity resultEntity = pagoLogic.getPago(dataCliente.get(1).getId(), entity.getId());
        resultEntity.setCliente(entity.getCliente());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
        Assert.assertEquals(entity.getValor(), resultEntity.getValor());

    }
    /**
     * prueba para actualizar un Pago
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updatePagoTest() throws BusinessLogicException {
        PagoEntity entity = data.get(0);
        PagoEntity pojoEntity = factory.manufacturePojo(PagoEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setFecha("10/11/2018");
        pojoEntity.setCliente(entity.getCliente());
        
        if(pojoEntity.getValor() <0){
            pojoEntity.setValor(pojoEntity.getValor()*-1);
        }
             
        pagoLogic.updatePago(pojoEntity.getCliente().getId(), pojoEntity);
        
        PagoEntity resp = em.find(PagoEntity.class, entity.getId());
        
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getValor(), resp.getValor());

    }

}
