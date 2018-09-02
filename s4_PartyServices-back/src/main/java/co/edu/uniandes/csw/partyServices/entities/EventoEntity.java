/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author estudiante
 */
@Entity
public class EventoEntity implements Serializable
{
    @Id
    private long Id ;
    
    private String estado ;
    
    @ManyToOne()
    private FechaEntity fecha ;
 
    private long latitud ;
    
    private long longitud ;
    
    @OneToMany(
    mappedBy = "evento" ,
    fetch = FetchType.LAZY
    )
    Collection<ProductoEntity> productos ;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public FechaEntity getFecha() {
        return fecha;
    }

    public void setFecha(FechaEntity fecha) {
        this.fecha = fecha;
    }

    public Collection<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(Collection<ProductoEntity> productos) {
        this.productos = productos;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }
    
   
    
}