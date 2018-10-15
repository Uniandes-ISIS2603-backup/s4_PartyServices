/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Entidad de fecha
 * @author Nicolas Hernandes
 */
@Entity
public class FechaEntity extends BaseEntity implements Serializable {
    
    
    /**
     * Relacion muchos a uno con agenda
     */
    @PodamExclude
    @ManyToOne()
    private AgendaEntity agenda;
    
    /**
     * Dia de la fecha
     */
    @Temporal(TemporalType.DATE)
    private Date dia;
    
    /**
     * Jornada de la fecha
     */
    private String jornada;
    
    /**
     * Eventos de la fecha
     */
    @PodamExclude
    @OneToMany(
            mappedBy="fecha",
            fetch= FetchType.LAZY
    )
    private Collection<EventoEntity> eventos;
  
    /**
     * Obtener agenda
     * @return la agenda de la fecha
     */
    public AgendaEntity getAgenda() {
        return agenda;
    }

    /**
     * Actualizar agenda
     * @param agenda la nueva agenda
     */
    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
    }
    
    /**
     * Obtener el dia de la fecha
     * @return el dia de la fecha
     */
    public Date getDia()
    {
        return dia;
    }
    
    /**
     * Obtener la jornada de la fecha
     * @return la jornada de la fecha
     */
    public String getJornada()
    {
        return jornada;
    }
    
    /**
     * Cambiar el dia de la fecha
     * @param dia el dia de la fecha
     */
    public void setDia(Date dia)
    {
        this.dia=dia;
    }
    
    /**
     * Cambiar la jornada de la fecha
     * @param jornada jornada de la fecha
     */
    public void setJornada(String jornada)
    {
        this.jornada=jornada;
    }
    /**
     * Obtener los eventos de la fecha
     * @return los eventos de la fecha
     */
    public Collection<EventoEntity> getEventos()
    {
        return eventos;
    }
    
    /**
     * Actualizar los eventos de la fecha
     * @param eventos eventos de la fecga
     */
    public void setEventos(Collection<EventoEntity> eventos)
    {
        this.eventos=eventos;
    }
    
}