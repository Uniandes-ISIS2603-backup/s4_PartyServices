/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import co.edu.uniandes.csw.partyServices.ejb.FechaEventoLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author Nicolas Hernandez
 */
@RunWith(Arquillian.class)
public class FechaEventoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private FechaEventoLogic fechaEventoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<FechaEntity> dataFecha = new ArrayList<FechaEntity>();
    private List<EventoEntity> dataEvento = new ArrayList<EventoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FechaEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(FechaEventoLogic.class.getPackage())
                .addPackage(FechaPersistence.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from FechaEntity").executeUpdate();
    }
    
     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            FechaEntity fecha = factory.manufacturePojo(FechaEntity.class);

            em.persist(fecha);

            dataFecha.add(fecha);
            
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);

            em.persist(evento);

            dataEvento.add(evento);
        }
    }
    @Test
    public void anadirEventoTest(){
        EventoEntity eventoAnadido = this.fechaEventoLogic.anadirEvento(dataFecha.get(0).getId(), dataEvento.get(0).getId());
        Assert.assertEquals(dataEvento.get(0).getId(), eventoAnadido.getId());
    }
    
    @Test
    public void reemplazarEventosTest(){
        Collection<EventoEntity>eventos=new ArrayList<EventoEntity>();
        eventos.add(dataEvento.get(0));
        eventos.add(dataEvento.get(1));
        eventos.add(dataEvento.get(2));
        Collection<EventoEntity> eventosAnadidos = this.fechaEventoLogic.remplazarEventos(dataFecha.get(0).getId(),eventos );
        Assert.assertEquals(3, eventosAnadidos.size());
    }
    @Test
    public void obtenerEventosTest(){
        Collection<EventoEntity>eventos=new ArrayList<EventoEntity>();
        eventos.add(dataEvento.get(0));
        eventos.add(dataEvento.get(1));
        eventos.add(dataEvento.get(2));
        this.fechaEventoLogic.remplazarEventos(dataFecha.get(0).getId(),eventos );
        Assert.assertEquals(3, this.fechaEventoLogic.obtenerEventos(dataFecha.get(0).getId()).size()  );
    }
    @Test
    public void eliminarEventoTest(){
        Collection<EventoEntity>eventos=new ArrayList<EventoEntity>();
        eventos.add(dataEvento.get(0));
        eventos.add(dataEvento.get(1));
        eventos.add(dataEvento.get(2));
        this.fechaEventoLogic.remplazarEventos(dataFecha.get(0).getId(),eventos );
        fechaEventoLogic.eliminarEvento(dataFecha.get(0).getId(), dataEvento.get(0).getId());
         Assert.assertEquals(2, this.fechaEventoLogic.obtenerEventos(dataFecha.get(0).getId()).size()  );
    }
    
}
