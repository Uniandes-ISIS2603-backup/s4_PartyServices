/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
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
import junit.framework.Assert;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class FechaLogicTest {
    
    private PodamFactory factory= new PodamFactoryImpl();
    
    @Inject
    private FechaLogic fechaLogic;
    
     @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<FechaEntity> data = new ArrayList<FechaEntity>();
   
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FechaEntity.class.getPackage())
                .addPackage(FechaLogic.class.getPackage())
                .addPackage(FechaPersistence.class.getPackage())
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
        em.createQuery("delete from FechaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            FechaEntity entity = factory.manufacturePojo(FechaEntity.class);
            AgendaEntity agenda =factory.manufacturePojo(AgendaEntity.class);
            em.persist(agenda);
            entity.setAgenda(agenda);
            em.persist(entity);

            data.add(entity);
        }
    }
    
    
    @Test
    public void crearFechaTest()
    {
        //Fecha valida
        try {
            
            FechaEntity fechaValida = factory.manufacturePojo(FechaEntity.class);
            AgendaEntity agenda = factory.manufacturePojo(AgendaEntity.class);
            utx.begin();
            em.persist(agenda);
            utx.commit();
            fechaValida.setAgenda(agenda);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
           
            fechaValida.setDia(dia);
            
            fechaValida.setJornada(ConstantesJornada.JORNADA_COMPLETA.darValor());
            ArrayList<EventoEntity> eventos = new ArrayList<>();
            EventoEntity evento=factory.manufacturePojo(EventoEntity.class);
            utx.begin();
            em.persist(evento);
            utx.commit();
            eventos.add(evento);
            fechaValida.setEventos(eventos);
            fechaLogic.createFecha(agenda.getId(), fechaValida);
        } catch (BusinessLogicException e) {
            Assert.fail("Deberia crear la fecha, "+e.getMessage());
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Fecha invalida por jornada 
        try {
            
            FechaEntity fechaValida = factory.manufacturePojo(FechaEntity.class);
            AgendaEntity agenda = factory.manufacturePojo(AgendaEntity.class);
            utx.begin();
            em.persist(agenda);
            utx.commit();
            fechaValida.setAgenda(agenda);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
           
            fechaValida.setDia(dia);
            
            fechaValida.setJornada("werdftgyh");
            ArrayList<EventoEntity> eventos = new ArrayList<>();
            EventoEntity evento=factory.manufacturePojo(EventoEntity.class);
            utx.begin();
            em.persist(evento);
            utx.commit();
            eventos.add(evento);
            fechaValida.setEventos(eventos);
            fechaLogic.createFecha(agenda.getId(), fechaValida);
            Assert.fail("NO Deberia crear la fecha ya que no cumple con la jornada");
        }  catch (BusinessLogicException |NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Fecha invalida por jornada igual a NINGUNA
        try {
            
            FechaEntity fechaValida = factory.manufacturePojo(FechaEntity.class);
            AgendaEntity agenda = factory.manufacturePojo(AgendaEntity.class);
            utx.begin();
            em.persist(agenda);
            utx.commit();
            fechaValida.setAgenda(agenda);
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
           
            fechaValida.setDia(dia);
            
            fechaValida.setJornada(ConstantesJornada.NINGUNA.darValor());
            ArrayList<EventoEntity> eventos = new ArrayList<>();
            EventoEntity evento=factory.manufacturePojo(EventoEntity.class);
            utx.begin();
            em.persist(evento);
            utx.commit();
            eventos.add(evento);
            fechaValida.setEventos(eventos);
            fechaLogic.createFecha(agenda.getId(), fechaValida);
            Assert.fail("NO Deberia crear la fecha ya que la jornada no puede ser ninguna");
        }  catch (BusinessLogicException |NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void obtenerFechaTest()
    {
        for (FechaEntity fechaEntity : data) {
            Assert.assertNotNull(fechaLogic.getFechaID(fechaEntity.getId()));
            Assert.assertNotNull(fechaLogic.getFechaPorDiaAgendaJornada(fechaEntity.getDia(),fechaEntity.getAgenda().getId(),fechaEntity.getJornada()));
        }
    }
    
    @Test
    public void actualizarFechaTest()
    {
        try {
            FechaEntity fecha = data.get(0);
            fecha.setJornada(ConstantesJornada.JORNADA_MANANA.darValor());
            fechaLogic.updateFecha(fecha);
        } catch (BusinessLogicException ex) {
            Assert.fail("Deberia actualizar la fecha");
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FechaEntity fecha = data.get(0);
            fecha.setJornada(ConstantesJornada.NINGUNA.darValor());
            fechaLogic.updateFecha(fecha);
             Assert.fail("NO deberia actualizar la fecha");
            
        } catch (BusinessLogicException ex) {
           Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void eliminarFechaTest()
    {
       try {
            FechaEntity fecha =factory.manufacturePojo(FechaEntity.class);
            fecha.setEventos(new ArrayList<>());
            utx.begin();
            em.persist(fecha);
            utx.commit();
            fechaLogic.deleteFecha(fecha.getId());
        } catch (BusinessLogicException ex) {
            Assert.fail("Deberia poder elimnar la fecha");
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       try {
            FechaEntity fecha = factory.manufacturePojo(FechaEntity.class);
            ArrayList<EventoEntity> listaEventos= new ArrayList();
            listaEventos.add(new EventoEntity());
            fecha.setEventos(listaEventos);
            utx.begin();
            em.persist(fecha);
            utx.commit();
            fechaLogic.deleteFecha(fecha.getId());
            Assert.fail("NO Deberia poder elimnar la fecha, la fecha tiene eventos");
           
        } catch (BusinessLogicException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
             Logger.getLogger(FechaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
