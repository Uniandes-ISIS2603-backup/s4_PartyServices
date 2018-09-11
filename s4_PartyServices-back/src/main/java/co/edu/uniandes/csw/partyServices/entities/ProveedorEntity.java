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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
/**
 *
 * @author estudiante
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable{
    private final static long serialVersionUID = 1L ;
   
    @OneToOne
    private AgendaEntity agenda;
    
    private String nombre;
    
    private String contrasenia;
    
    @ManyToOne
    public ServicioEntity servicio;
    
    @OneToMany(mappedBy="proveedor",
            fetch= FetchType.LAZY)
    Collection<ProductoEntity> catalogoProductos;
   
    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    Collection<NotificacionEntity> notificaciones;

    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    Collection<ValoracionEntity> valoraciones;
    
    public String getNombre() {
        return nombre;
    }
   
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
     public Collection<ProductoEntity> getProductos()
    {
        return catalogoProductos;
    }
    public void setProductos(Collection<ProductoEntity> catalogoProductos)
    {
        this.catalogoProductos=catalogoProductos;
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
    
    
}
    
    

      

