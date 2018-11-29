/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
     * Atributo que representa el dia en el que se realizará el evento 
     */
    private Date dia ;
    
    /**
     * Atributo que representa la jornada en la que se realizará el evento
     */
    private String jornada ;
    
    /**
     * Atributo que representa la relacion muchos a uno con la clase FechaEntity
     */
    @PodamExclude
    @ManyToMany
    private List<FechaEntity> fechas ;
    
    /**
     * Atributo que representa la relacion muchos a uno con la clase ClienteEntity
     */
    @PodamExclude
    @ManyToOne()
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
    private List<ProductoEntity> productos ;
    
    
    /**
     * Atributo que representa la relacion uno a muchos con la clase NotificacionEntity
     */
    @PodamExclude
    @OneToMany(
    mappedBy = "evento",
    fetch = FetchType.LAZY)
    private List<NotificacionEntity> notificaciones ;
    
    /**
     * Relacion uno a uno con el pago del evento.
     */
    @PodamExclude
    @OneToOne(
    mappedBy = "evento",
            fetch = FetchType.LAZY
    )
    private PagoEntity pago;

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
     * Metodo que retorna el dia del evento
     * @return dia
     */
    public Date getDia() {
        return dia;
    }

    /**
     * Metodo que modifica el dia del evento
     * @param dia 
     */
    public void setDia(Date dia) {
        this.dia = dia;
    }

    /**
     * Metodo que retorna la jornada del evento
     * @return jornada
     */
    public String getJornada() {
        return jornada;
    }

    /**
     * Metodo que modifica la jornada del evento 
     * @param jornada 
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    
    
    /**
     * Metodo que retorna la fecha del evento
     * @return fecha
     */
    public List<FechaEntity> getFechas() {
        return fechas;
    }

    /**
     * Metodo que modifica la fecha del evento
     * @param fechas 
     */
    public void setFechas(List<FechaEntity> fechas) {
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
    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Metodo que modifica el listado de notificaciones del evento
     * @param notificaciones 
     */
    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    /**
     * Metodo que retorna los productos del evento
     * @return productos
     */
    public List<ProductoEntity> getProductos() {
        return productos;
    }

    /**
     * Metodo que modifica la lista de productos del evento
     * @param productos 
     */
    public void setProductos(List<ProductoEntity> productos) {
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
    
    /**
     * Devuelve el pago hecho a este evento si lo tiene.
     * @return pago. El pago del evento.
     */
    public PagoEntity getPago() {
        return pago;
    }

    /**
     * Modifica el pago del evento.
     * @param pago. El nuevo pago que reemplazara al actual.
     */
    public void setPago(PagoEntity pago) {
        this.pago = pago;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nombre);
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
        final EventoEntity other = (EventoEntity) obj;
 
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
       
        
        return true;
    }
    
   
    
 
    
    
}