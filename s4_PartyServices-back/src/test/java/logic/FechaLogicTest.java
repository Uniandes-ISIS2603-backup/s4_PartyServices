/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            Date dia = new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(dia);
            cal.add(Calendar.DATE, 15);
            dia=cal.getTime();
           
            fechaValida.setDia(dia);
            fechaValida.setJornada(FechaEntity.Jornada.JORNADA_NOCHE.darValor());
            ArrayList<EventoEntity> eventos = new ArrayList<>();
            EventoEntity evento=factory.manufacturePojo(EventoEntity.class);
            em.persist(evento);
            eventos.add(evento);
            fechaValida.setEventos(eventos);
            fechaLogic.createFecha(3456789, fechaValida);
        } catch (BusinessLogicException e) {
            
            Assert.fail("Deberia crear la fecha"+e.getMessage());
        }
    }
    
    @Test
    public void obtenerFechaTest()
    {
        
    }
    
    @Test
    public void actualizarFechaTest()
    {
        
    }
    
    @Test
    public void eliminarFechaTest()
    {
        
    }
    
}
