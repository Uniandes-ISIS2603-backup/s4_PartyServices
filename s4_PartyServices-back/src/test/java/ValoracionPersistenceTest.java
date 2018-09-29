/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ValoracionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class ValoracionPersistenceTest {
    
    /**
     * Instancia de la clase PodamFactory que nos ayudará para crear datos aleatorios de las clases.
     */
    PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Inyección de la dependencia a la clase ValoracionPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    public ValoracionPersistence valoracionPersistence;
    
    /**
     * Inyección de la dependencia a la clase ProveedorPersistence cuyo método findAll nos va a servir.
     */
    @Inject
    public ProveedorPersistence proveedorPersistence;
    
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
     * Lista que tiene los datos de prueba para las valoraciones.
     */
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();
    
    /**
     * Lista que tiene los datos de prueba para los proveedores.
     */
    private List<ProveedorEntity> dataProveedor = new ArrayList<ProveedorEntity>();
    
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Sugerencia, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest(){
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
            
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData(){
        em.createQuery("Delete from ValoracionEntity").executeUpdate();
        em.createQuery("Delete from ProveedorEntity").executeUpdate();  
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        
        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(entity);
            dataProveedor.add(entity);
        }
        
        for(int i=0; i<3; i++){
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            if (i == 0) {
                entity.setProveedor(dataProveedor.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una Valoracion.
     */
    @Test
    public void createValoracionTest() {
        PodamFactory fabrica = new PodamFactoryImpl();
        ValoracionEntity nuevaEntidad = fabrica.manufacturePojo(ValoracionEntity.class);
        ValoracionEntity result = valoracionPersistence.create(nuevaEntidad);

        Assert.assertNotNull(result);

        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());

        Assert.assertEquals(nuevaEntidad.getComentario(), entity.getComentario());
        Assert.assertEquals(nuevaEntidad.getPuntaje(), entity.getPuntaje());
        Assert.assertEquals(nuevaEntidad.getNombreUsuario(), entity.getNombreUsuario());
    }
    
    /**
     * Prueba para consultar una Valoracion.
     */
    @Test
    public void getValoracionTest() {
        //ValoracionEntity entity = data.get(0) esto no funciona no sé por qué, algunas veces lanza out of bound exception;
        List<ValoracionEntity> listaValoraciones = valoracionPersistence.findAll();
        ValoracionEntity entity = null;
        ProveedorEntity proveedorEntity = null;
        for(int i=0; i<3; i++){
            ValoracionEntity valoracionEncontrada = listaValoraciones.get(i);
            ProveedorEntity proveedorEncontrado = valoracionEncontrada.getProveedor();
            if(proveedorEncontrado != null){
                entity = valoracionEncontrada;
                proveedorEntity = proveedorEncontrado;
                break;
            }
        }
        ValoracionEntity newEntity = valoracionPersistence.find(proveedorEntity.getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
        Assert.assertEquals(entity.getPuntaje(), newEntity.getPuntaje());
        Assert.assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para actualizar una Valoracion.
     */
    @Test
    public void updateValoracionTest() {
        //ValoracionEntity entity = data.get(2);
        ValoracionEntity entity = valoracionPersistence.findAll().get(2);
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);

        newEntity.setId(entity.getId());

        ValoracionEntity resp = valoracionPersistence.update(newEntity);

        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(newEntity.getPuntaje(), resp.getPuntaje());
        Assert.assertEquals(newEntity.getNombreUsuario(), resp.getNombreUsuario());
    }
    
    /**
     * Prueba para eliminar una Valoracion.
     */
    @Test
    public void deleteValoracionTest() {
        //ValoracionEntity entity = data.get(1);
        ValoracionEntity entity = valoracionPersistence.findAll().get(1);
        valoracionPersistence.delete(entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
}
