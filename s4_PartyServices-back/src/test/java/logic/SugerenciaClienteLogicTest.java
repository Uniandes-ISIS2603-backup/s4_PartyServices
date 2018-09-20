/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.SugerenciaClienteLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
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

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@RunWith(Arquillian.class)
public class SugerenciaClienteLogicTest {
    
    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
     */
    private PodamFactory factory;
    
    /**
     * Inyección de la dependencia a la clase SugerenciaClienteLogic cuyos métodos se
     * van a probar.
     */
    @Inject
    private SugerenciaClienteLogic sugerenciaClienteLogic;
    
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * Lista que tiene los datos de prueba para las sugerencias.
     */
    private List<SugerenciaEntity> dataSugerencia = new ArrayList<SugerenciaEntity>();
    
    /**
     * Lista que tiene los datos de prueba para los clientes.
     */
    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();
    
    /**
     * Lista que tiene los datos de prueba para las tematicas.
     */
    private List<TematicaEntity> dataTematica = new ArrayList<TematicaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(SugerenciaClienteLogic.class.getPackage())
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
        em.createQuery("delete from SugerenciaEntity").executeUpdate();
        em.createQuery("delete from TematicaEntity ").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        factory = new PodamFactoryImpl(); 
        for (int i = 0; i < 3; i++) {
            SugerenciaEntity sugerencia = factory.manufacturePojo(SugerenciaEntity.class);
            TematicaEntity tematica = factory.manufacturePojo(TematicaEntity.class);

            sugerencia.setTematica(tematica);
            tematica.getSugerencias().add(sugerencia);

            em.persist(tematica);
            dataTematica.add(tematica);
            em.persist(sugerencia);
            dataSugerencia.add(sugerencia);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            dataCliente.add(entity);
        }
        
        dataSugerencia.get(2).setCliente(dataCliente.get(2));
        em.merge(dataSugerencia.get(2));
    }
    
    /**
     * Prueba para asociar una sugerencia a un cliente.
     */
    @Test
    public void addClienteTest() {
        ClienteEntity clienteEntity = dataCliente.get(0);
        SugerenciaEntity sugerenciaEntity = dataSugerencia.get(0);
        ClienteEntity response = sugerenciaClienteLogic.addCliente(dataTematica.get(0).getId(), sugerenciaEntity.getId(), clienteEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(clienteEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para consultar un cliente de una sugerencia.
     */
    @Test
    public void getClienteTest() {
        SugerenciaEntity entity = dataSugerencia.get(2);
        ClienteEntity resultEntity = sugerenciaClienteLogic.getCliente(dataTematica.get(2).getId(), entity.getId());
        
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getCliente().getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para remplazar el cliente de una sugerencia.
     */
    @Test
    public void replaceAuthorTest() {
        ClienteEntity clienteEntity = dataCliente.get(2);
        ClienteEntity newClienteEntity = sugerenciaClienteLogic.replaceCliente(dataTematica.get(2).getId(), dataSugerencia.get(2).getId(), clienteEntity.getId());
        
        Assert.assertEquals(clienteEntity,newClienteEntity);
    }
    
    /**
     * Prueba para desasociar un cliente de una sugerencia.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void removeClienteTest() throws BusinessLogicException {
        sugerenciaClienteLogic.removeCliente(dataTematica.get(2).getId(), dataSugerencia.get(2).getId());
        Assert.assertNull(sugerenciaClienteLogic.getCliente(dataTematica.get(2).getId(), dataSugerencia.get(2).getId()));
    }
    
    /**
     * Prueba para desasociar un cliente a una sugerencia existente sin cliente.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeClienteInexistenteTest() throws BusinessLogicException {
    
        sugerenciaClienteLogic.removeCliente(dataTematica.get(0).getId(),dataSugerencia.get(0).getId());
    }
}
