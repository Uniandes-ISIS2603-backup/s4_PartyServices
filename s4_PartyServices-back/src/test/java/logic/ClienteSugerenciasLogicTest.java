/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ClienteLogic;
import co.edu.uniandes.csw.partyServices.ejb.ClienteSugerenciasLogic;
import co.edu.uniandes.csw.partyServices.ejb.SugerenciaLogic;
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
public class ClienteSugerenciasLogicTest {
    
    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
     */
    private PodamFactory factory;
    
    /**
     * Inyección de la dependencia a la clase ClienteSugerenciasLogic cuyos métodos se
 van a probar.
     */
    @Inject
    private ClienteSugerenciasLogic clienteSugerenciaLogic;
    
    @Inject
    private ClienteLogic clienteLogic;
    
     @Inject
    private SugerenciaLogic sugerenciaLogic;
    
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
                .addPackage(ClienteSugerenciasLogic.class.getPackage())
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
    public void addSugerenciaTest() {
        ClienteEntity clienteEntity = dataCliente.get(0);
        SugerenciaEntity sugerenciaEntity = dataSugerencia.get(0);
        SugerenciaEntity response = clienteSugerenciaLogic.addSugerencia(dataTematica.get(0).getId(), sugerenciaEntity.getId(), clienteEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(sugerenciaEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Sugerencias asociadas a una
     * instancia de Cliente.
     */
    @Test
    public void getSugerenciasTest() {
        List<SugerenciaEntity> list = clienteSugerenciaLogic.getSugerencias(dataCliente.get(2).getId());

        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de sugrencia asociada a una instancia
     * Cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getSugerenciaTest() throws BusinessLogicException {
        ClienteEntity entity = dataCliente.get(2);
        SugerenciaEntity sugerenciaEntity = dataSugerencia.get(2);
        SugerenciaEntity response = clienteSugerenciaLogic.getSugerencia(sugerenciaEntity.getTematica().getId(), sugerenciaEntity.getId(), entity.getId());

        Assert.assertEquals(sugerenciaEntity.getId(), response.getId());
        Assert.assertEquals(sugerenciaEntity.getComentario(), response.getComentario());
        Assert.assertEquals(sugerenciaEntity.getNombreUsuario(), response.getNombreUsuario());
    }
    
    /**
     * Prueba para obtener una instancia de Sugerencias asociada a una instancia
     * Cliente que no le pertenece.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getSugerenciaNoAsociadaTest() throws BusinessLogicException {
        ClienteEntity entity = dataCliente.get(0);
        SugerenciaEntity sugerenciaEntity = dataSugerencia.get(1);
        clienteSugerenciaLogic.getSugerencia(sugerenciaEntity.getTematica().getId(), sugerenciaEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para remover la relación entre un cliente y sus sugerencias.
     */
    @Test
    public void removeSugerenciasTest() {
        ClienteEntity entity = dataCliente.get(2);
        clienteSugerenciaLogic.removeSugerencias(entity.getId());
        
        SugerenciaEntity sugerenciaEntity = sugerenciaLogic.getSugerencia(dataSugerencia.get(2).getTematica().getId(), dataSugerencia.get(2).getId());
        
        Assert.assertNull(sugerenciaEntity.getCliente());
        Assert.assertEquals("Anónimo", sugerenciaEntity.getNombreUsuario());
    }
}
