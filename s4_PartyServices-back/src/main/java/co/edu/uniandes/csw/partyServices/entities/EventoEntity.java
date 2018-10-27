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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Andres
 */
@Entity
public class EventoEntity  extends BaseEntity implements Serializable
{    
    /**
     * Atributo que representa el nombre de un evento
     */    
    private String nombre ;
    
    /**
     * Atributo que representa el estado de un evento, solo puede tomar los valores:
     *   "En planeacion" ,  "Planeado" ,  "En proceso",  "Cancelado",  "Terminado"
     */
    private String estado ;
    
    /**
     * Atributo que representa la relacion muchos a uno con la clase FechaEntity
     */
    @PodamExclude
    @ManyToMany
    private Collection<FechaEntity> fechas ;
    
    /**
     * Atributo que representa la relacion muchos a uno con la clase ClienteEntity
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private ClienteEntity cliente ;
 
     /**
     * Atributo que representa la latitud de un evento, necesario para determinar su ubicación
     */
    private double latitud ;
    
    /**
     * Atributo que representa la longitud de un evento, necesario para determinar su ubicación
     */    
    private double longitud ;
    
    
    /**
     * Atributo que representa la relacion muchos a muchos con la clase ProductoEntity
     */
    @PodamExclude    
    @ManyToMany(
    mappedBy = "eventos" ,
    fetch = FetchType.LAZY
    )
    private Collection<ProductoEntity> productos ;
    
    
    /**
     * Atributo que representa la relacion uno a muchos con la clase NotificacionEntity
     */
    @PodamExclude
    @OneToMany(
    mappedBy = "evento",
    fetch = FetchType.LAZY)
    private Collection<NotificacionEntity> notificaciones ;

    /**
     * Metodo que retorna el nombre del evento
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que modifica el nombre del evento
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    /**
     * Metodo que retorna el estado del evento
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Metodo que modifica el estado del evento
     * @param estado 
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    /**
     * Metodo que retorna la fecha del evento
     * @return fecha
     */
    public Collection<FechaEntity> getFechas() {
        return fechas;
    }

    /**
     * Metodo que modifica la fecha del evento
     * @param fechas 
     */
    public void setFechas(Collection<FechaEntity> fechas) {
        this.fechas = fechas;
    }

    /**
     * Metodo que retorna el cliente del evento
     * @return cliente
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * Metodo que modifica el cliente del evento
     * @param cliente 
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * Metodo que retorna las notificaciones del evento
     * @return notificaciones
     */
    public Collection<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Metodo que modifica el listado de notificaciones del evento
     * @param notificaciones 
     */
    public void setNotificaciones(Collection<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    /**
     * Metodo que retorna los productos del evento
     * @return productos
     */
    public Collection<ProductoEntity> getProductos() {
        return productos;
    }

    /**
     * Metodo que modifica la lista de productos del evento
     * @param productos 
     */
    public void setProductos(Collection<ProductoEntity> productos) {
        this.productos = productos;
    }
    
    /**
     * Metodo que agrega un producto al listado de productos
     * @param producto 
     */
    public void agregarProducto(ProductoEntity producto)
    {
        if(productos==null)
        {
            productos = new ArrayList<>();
            productos.add(producto) ;
        }
        else
        {
            productos.add(producto) ;
        }
    }

    /**
     * Metodo que retorna la latitud del evento
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Metodo que modifica la latitud del evento
     * @param latitud 
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Metodo que retorna la longitud del evento
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Metodo que modifica la longitud del evento
     * @param longitud 
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
   
    
}
