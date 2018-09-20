/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.ValoracionLogic;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
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
public class ValoracionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Inyección de la dependencia a la clase ValoracionLogic cuyos métodos se
     * van a probar.
     */
    @Inject
    private ValoracionLogic valoracionLogic;

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
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();
    
    /**
     * Lista que tiene los datos de prueba para crear un proveedor con muchas valoraciones.
     */
    private List<ValoracionEntity> dataMuchasValoraciones = new ArrayList<ValoracionEntity>();
    
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
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionLogic.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ValoracionEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 2; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(entity);
            dataProveedor.add(entity);
        }
        
        for (int i = 0; i < 2; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            entity.setProveedor(dataProveedor.get(i));
            em.persist(entity);
            data.add(entity);
        }
        
        ProveedorEntity proveedorEntityListaValoraciones = factory.manufacturePojo(ProveedorEntity.class);
        em.persist(proveedorEntityListaValoraciones);
        
        for (int i = 0; i < 10; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            entity.setProveedor(proveedorEntityListaValoraciones);
            em.persist(entity);
            dataMuchasValoraciones.add(entity);
        }
      
        dataProveedor.add(proveedorEntityListaValoraciones); //dataProveedor(2) = proveedorEntityListaValoraciones
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
     * Prueba para crear una Valoracion.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createValoracionTest() throws BusinessLogicException {
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);
        newEntity.setProveedor(dataProveedor.get(2));
        
        ValoracionEntity result = valoracionLogic.createValoracion(newEntity.getProveedor().getId(), newEntity);
        Assert.assertNotNull(result);
        
        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertEquals(newEntity.getPuntaje(), entity.getPuntaje());
        Assert.assertEquals(newEntity.getProveedor(), entity.getProveedor());
    }
    
    /**
     * Prueba para crear una Valoracion con más caracteres que el límite.
     * 
     * @throws BusinessLogicException
     */
    @Test (expected= BusinessLogicException.class)
    public void createValoracionLimiteCaracteresTest() throws BusinessLogicException {
        String mensajeGrande = "hola";
        for(int i=0; i<3000; i++){
            mensajeGrande = mensajeGrande.concat("hola");
        }
        ValoracionEntity newEntity = new ValoracionEntity();
        newEntity.setComentario(mensajeGrande);
        newEntity.setPuntaje(5);
        newEntity.setProveedor(dataProveedor.get(0));
        ValoracionEntity result = valoracionLogic.createValoracion(newEntity.getProveedor().getId(), newEntity);
    }
    
    /**
     * Prueba para consultar la lista de valoraciones.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getValoracionesTest() throws BusinessLogicException {
        List<ValoracionEntity> list = valoracionLogic.getValoraciones(dataProveedor.get(2).getId());
        Assert.assertEquals(dataMuchasValoraciones.size(), list.size());
        for (ValoracionEntity entity : list) {
            boolean found = false;
            for (ValoracionEntity storedEntity : dataMuchasValoraciones) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una valoracion.
     */
    @Test
    public void getValoracionTest() {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity resultEntity = valoracionLogic.getValoracion(dataProveedor.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getPuntaje(), resultEntity.getPuntaje());
    }
    
    /**
     * Prueba para actualizar una Valoracion.
     * @throws BusinessLogicException
     */
    @Test
    public void updateValoracionTest() throws BusinessLogicException{
        ValoracionEntity entity = data.get(1);
        ValoracionEntity pojoEntity = factory.manufacturePojo(ValoracionEntity.class);

        pojoEntity.setId(entity.getId());

        valoracionLogic.updateValoracion(dataProveedor.get(1).getId(), pojoEntity);

        ValoracionEntity resp = em.find(ValoracionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(pojoEntity.getPuntaje(), resp.getPuntaje());
    }
    
    /**
     * Prueba para actualizar una valoracion con más caracteres que el límite.
     * 
     * @throws BusinessLogicException
     */
    @Test (expected= BusinessLogicException.class)
    public void updateSugerenciaLimiteCaracteresTest() throws BusinessLogicException {
        String mensajeGrande = "hola";
        for(int i=0; i<5001; i++){
            mensajeGrande = mensajeGrande.concat("hola");
        }
        ValoracionEntity newEntity = new ValoracionEntity();
        newEntity.setComentario(mensajeGrande);
        newEntity.setProveedor(dataProveedor.get(0));
        ValoracionEntity result = valoracionLogic.updateValoracion(newEntity.getProveedor().getId(), newEntity);
    }
    
    /**
     * Prueba para eliminar una valoracion.
     *
     * @throws BusinessLogicException Si alguna valoracion no está asociada con el proveedor pasado por parámetro en el método deleteValoracion.
     */
    @Test
    public void deleteProveedorTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(1);
        valoracionLogic.deleteValoracion(dataProveedor.get(1).getId(), entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
    
    /**
     * Prueba para eliminarle una valoracion a un proveedor del cual no pertenece.
     *
     * @throws BusinessLogicException Si alguna valoracion no está asociada con el proveedor pasado por parámetro en el método deleteValoracion.
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteValoracionConProveedorNoAsociadoTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        valoracionLogic.deleteValoracion(dataProveedor.get(1).getId(), entity.getId());
    }
    
}
