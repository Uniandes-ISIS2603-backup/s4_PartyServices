/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * DD:MM:AAAA
     */
    private String fechaPenitencia;

    /**
     * L-lunes
     * M-martes
     * I-miercoles
     * J-jueves
     * V-viernes
     * S-sabado
     * D-domingo
     * Formato: L-M-D:JORNDADA
     */
    private String fechasNoDisponibles;
    @PodamExclude
    @OneToMany(
            mappedBy="agenda",
            cascade = CascadeType.ALL,
            //Terminan en many son lazy. Eger terminan en One
            fetch= FetchType.LAZY
    )
    
    Collection<FechaEntity> fechasOcupadas;
    
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }
    
    public String getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    
    public void setFechaPenitencia(String fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    
    public String getFechasNoDisponibles(){
        return fechasNoDisponibles;
    }
    
    public void setFechasNoDisponibles(String fechasNoDisponibles)
    {
        this.fechasNoDisponibles=fechasNoDisponibles;
    }
    
    public Collection<FechaEntity> getFechasOcupadas()
    {
        return fechasOcupadas;
    }
    
    public void setFechasOcupadas(Collection<FechaEntity> fechasOcupadas)
    {
        this.fechasOcupadas=fechasOcupadas;
    }
    
}
