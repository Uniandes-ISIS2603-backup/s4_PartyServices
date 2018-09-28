/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ClienteValoracionesLogic;
import co.edu.uniandes.csw.partyServices.ejb.ValoracionLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
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
public class ClienteValoracionesLogicTest {
    
    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
     */
    private PodamFactory factory = new PodamFactoryImpl();;
    
    /**
     * Inyección de la dependencia a la clase ClienteValoracionesLogic cuyos métodos se
     *van a probar.
     */
    @Inject
    private ClienteValoracionesLogic clienteValoracionesLogic;
    
    @Inject
    private ValoracionLogic valoracionLogic;
    
    @Inject 
    private ValoracionPersistence valoracionPersistence;
    
    @Inject 
    private ClientePersistence clientePersistence;
    
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
     * Lista que tiene los datos de prueba para las valoraciones.
     */
    private List<ValoracionEntity> dataValoracion = new ArrayList<ValoracionEntity>();

    /**
     * Lista que tiene los datos de prueba para los clientes.
     */
    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();

    /**
     * Lista que tiene los datos de prueba para los proveedores.
     */
    private List<ProveedorEntity> dataProveedor = new ArrayList<ProveedorEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteValoracionesLogic.class.getPackage())
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
        em.createQuery("delete from ValoracionEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity ").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
    
        for (int i = 0; i < 3; i++) {
            ValoracionEntity valoracion = factory.manufacturePojo(ValoracionEntity.class);
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);

            valoracion.setProveedor(proveedor);
            proveedor.setValoraciones(new ArrayList<ValoracionEntity>());
            proveedor.getValoraciones().add(valoracion);

            em.persist(proveedor);
            dataProveedor.add(proveedor);
            em.persist(valoracion);
            dataValoracion.add(valoracion);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            dataCliente.add(entity);
        }

        dataValoracion.get(2).setCliente(dataCliente.get(2));
        em.merge(dataValoracion.get(2));
    }
    
    /**
     * Prueba para asociar una valoracion a un cliente.
     */
    @Test
    public void addValoracionTest() {
        ClienteEntity clienteEntity = dataCliente.get(0);
        ValoracionEntity valoracionEntity = dataValoracion.get(0);
        
        ValoracionEntity response = clienteValoracionesLogic.addValoracion(valoracionEntity.getProveedor().getId(), valoracionEntity.getId(), clienteEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(valoracionEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Valoracion asociadas
     * a una instancia de Cliente.
     */
    @Test
    public void getValoracionesTest() {
        ClienteEntity clienteEntity = dataCliente.get(2);
        
        List<ValoracionEntity> list = clienteValoracionesLogic.getValoraciones(clienteEntity.getId());

        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de valoracion asociada a una instancia
     * Cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getValoracionTest() throws BusinessLogicException {
        ClienteEntity clienteEntity = dataCliente.get(2);
        ValoracionEntity valoracionEntity = dataValoracion.get(2);

        ValoracionEntity response = clienteValoracionesLogic.getValoracion(valoracionEntity.getProveedor().getId(), valoracionEntity.getId(), clienteEntity.getId());

        Assert.assertEquals(valoracionEntity.getId(), response.getId());
        Assert.assertEquals(valoracionEntity.getComentario(), response.getComentario());
        Assert.assertEquals(valoracionEntity.getPuntaje(), response.getPuntaje());
        Assert.assertEquals(valoracionEntity.getNombreUsuario(), response.getNombreUsuario());
    }
    
    /**
     * Prueba para obtener una instancia de Valoracion asociada a una instancia
     * Cliente que no le pertenece.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getValoracionNoAsociadaTest() throws BusinessLogicException {
        ClienteEntity clienteEntity = dataCliente.get(0);
        ValoracionEntity valoracionEntity = dataValoracion.get(1);
        
        clienteValoracionesLogic.getValoracion(valoracionEntity.getProveedor().getId(), valoracionEntity.getId(), clienteEntity.getId());
    }
    
    /**
     * Prueba para remover la relación entre un cliente y sus valoraciones.
     */
    @Test
    public void removeSugerenciasTest() {
       ClienteEntity clienteEntity = dataCliente.get(2);
       ValoracionEntity valoracionEntity = dataValoracion.get(2);

        clienteValoracionesLogic.removeValoraciones(clienteEntity.getId());

        ValoracionEntity resultado = valoracionLogic.getValoracion(valoracionEntity.getProveedor().getId(), valoracionEntity.getId());

        Assert.assertNull(resultado.getCliente());
        Assert.assertEquals("Anonimo", resultado.getNombreUsuario());
    }
}
