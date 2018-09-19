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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable{
   
    
    public enum DiaSemana{
        LUNES("LUNES"),
        MARTES("MARTES"),
        MIERCOLES("MIERCOLES"),
        JUEVES("JUEVES"),
        VIERNES("VIERNES"),
        SABADO("SABADO"),
        DOMINGO("DOMINGO");
        
        private final String valor;
        
        private DiaSemana(String valor){
            this.valor=valor;
        }
        
        public static DiaSemana desdeValor(String valor){
            for (DiaSemana diaSemana :  values()) {
                if(diaSemana.darValor().equals(valor)){
                    return diaSemana;
                }
            }
            return null;
        }
        
        public String darValor(){
            return valor;
        }
        
    }
    
    
    
    
    /**
     * DD:MM:AAAA
     */
    private Date fechaPenitencia;

    
    private String fechasNoDisponibles;
    @PodamExclude
    @OneToMany(
            mappedBy="agenda",
            cascade = CascadeType.ALL, 
            orphanRemoval =true ,
            //Terminan en many son lazy. Eger terminan en One
            fetch= FetchType.LAZY
    )
    private Collection<FechaEntity> fechasOcupadas;
    
    @PodamExclude
    @OneToOne(
            mappedBy="agenda",
            fetch = FetchType.EAGER
    )
    private ProveedorEntity proveedor;
             
    
    
    public Date getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    
    public void setFechaPenitencia(Date fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    
    public String getFechasNoDisponibles()
    {
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
    
    public ProveedorEntity getProveedor()
    {
        return proveedor;
    }
    
    public void setProveeedor(ProveedorEntity proveedor)
    {
        this.proveedor=proveedor;
    }
    
}
