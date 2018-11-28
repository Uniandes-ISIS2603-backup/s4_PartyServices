/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author estudiante
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable{
    
    private static final long serialVersionUID = 1L ;
    
    @PodamExclude
    @OneToOne(
    mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    private AgendaEntity agenda;
    
    private String nombre;
    
    private String contrasenia;
    
    private Double calificacion;
    
    @PodamExclude
    @ManyToOne
    public ServicioEntity servicio;
    
    @PodamExclude
    @OneToMany(mappedBy = "proveedor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, 
            orphanRemoval =true 
    )
    private Collection<ProductoEntity> catalogoProductos;
   
    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            cascade = CascadeType.ALL,
            orphanRemoval =true,
            fetch = FetchType.LAZY
    )
    private Collection<NotificacionEntity> notificaciones;
  
    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Collection<ValoracionEntity> valoraciones ;
    
    public String getNombre() {
        return nombre;
    }
   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    
    
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
   
    public AgendaEntity getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
    }

    public ServicioEntity getServicio() {
        return servicio;
    }

    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }

    public Collection<ProductoEntity> getCatalogoProductos() {
        return catalogoProductos;
    }

    public void setCatalogoProductos(Collection<ProductoEntity> catalogoProductos) {
        this.catalogoProductos = catalogoProductos;
    }

    public Collection<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Collection<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Collection<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Collection<ValoracionEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

   

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
        final ProveedorEntity other = (ProveedorEntity) obj;
        
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.contrasenia, other.contrasenia)) {
            return false;
        }
        if (!Objects.equals(this.agenda, other.agenda)) {
            return false;
        }
        if (!Objects.equals(this.calificacion, other.calificacion)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.catalogoProductos, other.catalogoProductos)) {
            return false;
        }
        if (!Objects.equals(this.notificaciones, other.notificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoraciones, other.valoraciones)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
    
    

      