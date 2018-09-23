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
public class ClienteLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ClienteLogic ClienteLogic;
    
    @PersistenceContext
    private EntityManager  em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList();
    
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
        for (int i = 0; i < 3; i++) 
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        
       
    }
    
    /**
     * Prueba para crear un Cliente.
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("21/10/1997");
        newEntity.setEmail("aaaaaaaa@udad.com");
        newEntity.setLogin("lololololololo");
        newEntity.setContrasenia("aaaaaaaa");
        ClienteEntity result = ClienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    
    
    
    /**
     * prueba para eliminar un Cliente
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @Test
    public void deleteClienteTest() throws BusinessLogicException{
        ClienteEntity entity = data.get(0);
        ClienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleteado = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleteado);
    }
    
    
    
    
        
        
      
    
    
    /**
     * Prueba para crear un Cliente con un login repetido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void loginRepetidoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin(data.get(0).getLogin());
        
        ClienteLogic.createCliente(newEntity);
    }
    
    /**
     * prueba para probar crear un Cliente con una  fecha de nacimiento superior a la actual
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void fechaSuperiorTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("21/10/2100");
        
        ClienteLogic.createCliente(newEntity);
    }
    
    /**
     * prueba para actualizar la fecha de nacimiento de un Cliente
     * @throws BusinessLogicException 
     
    public void updateFechaNacimientoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        
        newEntity.setFechaNacimiento("22/11/2000");
        ClienteLogic.createCliente(newEntity);
        
        
        newEntity.setFechaNacimiento("22/11/2001");
        
        ClienteLogic.updateCliente(newEntity);

         Assert.assertEquals("22/11/2001", newEntity.getFechaNacimiento());

        
    }
    */
    @Test(expected = BusinessLogicException.class)
    public void fechaFormatoIncorrectoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setFechaNacimiento("0130/2008");
       
        ClienteLogic.createCliente(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void loginFormatoIncorrectoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setLogin("");
        ClienteLogic.createCliente(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void contraseniaFormatoIncorrectoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasenia("++++");
       
        ClienteLogic.createCliente(newEntity);
    }
    

       
    
    
    
    /**
     * Prueba para crear un Cliente con un mail que no cumple el formato
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void mailFormatoIncorrectoTest() throws BusinessLogicException{
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ;
        newEntity.setEmail("lolololo@com");
        ClienteLogic.createCliente(newEntity);
    }
    

}