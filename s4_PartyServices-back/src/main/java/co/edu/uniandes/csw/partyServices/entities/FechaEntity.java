/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author estudiante
 */
@Entity
public class FechaEntity {
    @Id
    private Long id;
    
    private AgendaEntity agenda;
    
    private String dia;
    
    private String jornada;
    
    @OneToMany(
            mappedBy="fecha",
            fetch= FetchType.LAZY
    )
    Collection<EventoEntity> eventos;
    
    public String getDia()
    {
        return dia;
    }
    public String getJornada()
    {
        return jornada;
    }
    public void setDia(String dia)
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
    
}