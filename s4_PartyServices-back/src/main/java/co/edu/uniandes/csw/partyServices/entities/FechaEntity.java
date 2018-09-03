/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author estudiante
 */
@Entity
public class FechaEntity extends BaseEntity implements Serializable {
    
    
    
    @ManyToOne()
    private AgendaEntity agenda;
    
    @Temporal(TemporalType.DATE)
    private Date dia;
    
    private String jornada;
    
    @OneToMany(
            mappedBy="fecha",
            fetch= FetchType.LAZY
    )
    Collection<EventoEntity> eventos;
  
    public AgendaEntity getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
    }
      
    public Date getDia()
    {
        return dia;
    }
    public String getJornada()
    {
        return jornada;
    }
    public void setDia(Date dia)
    {
        this.dia=dia;
    }
    public void setJornada(String jornada)
    {
        this.jornada=jornada;
    }
    public Collection<EventoEntity> getEventos()
    {
        return eventos;
    }
    public void setEventos(Collection<EventoEntity> eventos)
    {
        this.eventos=eventos;
    }
    
    
}