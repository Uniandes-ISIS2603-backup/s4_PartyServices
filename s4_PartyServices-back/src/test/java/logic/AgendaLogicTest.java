/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.AgendaLogic;
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.AgendaPersistence;
import co.edu.uniandes.csw.partyServices.util.ConstantesJornada;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
public class AgendaLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private AgendaLogic agendaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<AgendaEntity> data = new ArrayList<AgendaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(AgendaLogic.class.getPackage())
                .addPackage(AgendaPersistence.class.getPackage())
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
            em.joinTransaction();
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
        em.createQuery("delete from AgendaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void crearAgendaTest()
    {
        //Agenda valida
        try {    
            AgendaEntity agendaValida=factory.manufacturePojo(AgendaEntity.class);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
            agendaValida.setFechaPenitencia(dia);
            
           
            agendaValida.setJornadaLunesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaMartesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaMiercolesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaJuevesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaViernesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaSabadoND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaDomingoND(ConstantesJornada.NINGUNA.darValor());
            
            
            
            
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            utx.begin();
            em.persist(proveedor);
            utx.commit();
            
            agendaLogic.createAgenda(proveedor.getId(), agendaValida);
            data.add(agendaValida);
        } catch (BusinessLogicException ex) {
            Assert.fail("Deberia crear la agenda"+ex);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Agenda invalida debido a fechaLunes no valida 
        try {    
            AgendaEntity agendaValida=factory.manufacturePojo(AgendaEntity.class);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
            agendaValida.setFechaPenitencia(dia);
            agendaValida.setJornadaLunesND("ewrtyuiomjhas");
            agendaValida.setJornadaMartesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaMiercolesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaJuevesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaViernesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaSabadoND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaDomingoND(ConstantesJornada.NINGUNA.darValor());
            
            
            
            
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            utx.begin();
            em.persist(proveedor);
            utx.commit();
            
            agendaLogic.createAgenda(proveedor.getId(), agendaValida);
            data.add(agendaValida);
            Assert.fail("NO Deberia crear la agenda");
        }  catch (BusinessLogicException |NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //Agenda invalida debido a fecha penitencia mayor al mes
            AgendaEntity agendaINValida=factory.manufacturePojo(AgendaEntity.class);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.MONTH, 15);
            dia=cal.getTime();
            agendaINValida.setFechaPenitencia(dia);
            
            agendaINValida.setJornadaLunesND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaMartesND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaMiercolesND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaJuevesND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaViernesND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaSabadoND(ConstantesJornada.NINGUNA.darValor());
            agendaINValida.setJornadaDomingoND(ConstantesJornada.NINGUNA.darValor());
           
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            utx.begin();
            em.persist(proveedor);
            utx.commit();
            
            agendaLogic.createAgenda(proveedor.getId(), agendaINValida);
                Assert.fail("no deberia crear la agenda");
        
        } catch (BusinessLogicException ex) {
        
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            //Agenda invalida debido al formato de fechas laborales
            AgendaEntity agendaValida=factory.manufacturePojo(AgendaEntity.class);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.MONTH, 15);
            dia=cal.getTime();
            agendaValida.setFechaPenitencia(dia);
            
           
            agendaValida.setJornadaLunesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaMartesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaMiercolesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaJuevesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaViernesND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaSabadoND(ConstantesJornada.NINGUNA.darValor());
            agendaValida.setJornadaDomingoND(ConstantesJornada.NINGUNA.darValor());
            
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            utx.begin();
            em.persist(proveedor);
            utx.commit();
            
            agendaLogic.createAgenda(proveedor.getId(), agendaValida);
                Assert.fail("no deberia crear la agenda");
        
        } catch (BusinessLogicException ex) {
        
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Test
    public void obtenerAgendaTest()
    {
        for (AgendaEntity agendaEntity : data) {
            try {
                Assert.assertNotNull(agendaLogic.getAgenda(agendaEntity.getId()));
            } catch (BusinessLogicException ex) {
                Assert.fail(ex.getMessage()+"No deberia mandar ecepcion");
            }
        }
    }
    @Test
    public void eliminarAgendaTest()
    {
        AgendaEntity agenda =  data.get(0);
        agendaLogic.deleteAgenda(agenda.getId());
        try {
            agendaLogic.getAgenda(agenda.getId());
            Assert.fail("Deberia mandar excepcion porque no existe la agenda");
        } catch (BusinessLogicException ex) {
            
        }
        data.remove(0);
    
    }
    @Test
    public void actualizarAgendaTest()
    {
        try {
            AgendaEntity agenda = agendaLogic.getAgenda(data.get(0).getId());
        Date dia = new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(dia);
        cal.add(Calendar.DATE, 10);
        dia=cal.getTime();
        agenda.setFechaPenitencia(dia);
     
      
            agenda.setJornadaLunesND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaMartesND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaMiercolesND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaJuevesND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaViernesND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaSabadoND(ConstantesJornada.NINGUNA.darValor());
            agenda.setJornadaDomingoND(ConstantesJornada.NINGUNA.darValor());
            
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            utx.begin();
            em.persist(proveedor);
            utx.commit();
            agendaLogic.updateAgenda(agenda);
            
        } catch (BusinessLogicException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Deberia actualizar la agenda, "+ex.getMessage());
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(AgendaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    

   
    
    
    
    
}
