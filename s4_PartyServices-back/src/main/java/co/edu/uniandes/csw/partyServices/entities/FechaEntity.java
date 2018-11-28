/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @ManyToMany(
            mappedBy="fechas"
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

    /**
     * Hash
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.agenda);
        hash = 17 * hash + Objects.hashCode(this.dia);
        return hash;
    }

    /**
     * Equals
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FechaEntity other = (FechaEntity) obj;
        if (!Objects.equals(this.jornada, other.jornada)) {
            return false;
        }
        if (!Objects.equals(this.agenda, other.agenda)) {
            return false;
        }
        if (!Objects.equals(this.dia, other.dia)) {
            return false;
        }
        if (!Objects.equals(this.eventos, other.eventos)) {
            return false;
        }
        return true;
    }
    
    
    
}