/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class ProductoEntity extends BaseEntity implements Serializable
{
    private final static long serialVersionUID = 1L ;
   
    
    private String nombre ;
    
    private String tipoServicio ;
    
    private String dueño ;
    
    @ManyToOne
    private ProveedorEntity proveedor ;
    
    private int costo ;
    
    private int cantidad  ;

    @PodamExclude
    @ManyToOne()
    EventoEntity evento ;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
    
    
    
    
    
    
}
