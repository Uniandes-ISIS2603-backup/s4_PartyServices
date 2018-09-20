/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private final static long serialVersionUID = 1L ;
    
    @PodamExclude
    @OneToOne()
    private AgendaEntity agenda;
    
    private String nombre;
    
    private String contrasenia;
    
    @PodamExclude
    @ManyToOne
    public ServicioEntity servicio;
    
    @PodamExclude
    @OneToMany(mappedBy = "proveedor",
            fetch = FetchType.LAZY)
    Collection<ProductoEntity> catalogoProductos = new ArrayList <ProductoEntity>();
   
    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            cascade = CascadeType.ALL,
            orphanRemoval =true,
            fetch = FetchType.LAZY
    )
    Collection<NotificacionEntity> notificaciones;
  
    @PodamExclude
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

    public Collection<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Collection<ValoracionEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }
    
    
    
}
    
    

      