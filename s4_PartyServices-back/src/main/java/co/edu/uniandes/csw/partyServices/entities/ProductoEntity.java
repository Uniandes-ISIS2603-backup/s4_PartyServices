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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres
 */
@Entity
public class ProductoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo que representa el nombre de un producto
     */
    private String nombre;

    /**
     *Atributo que representa el tipo de servicio de un producto
     */
    private String tipoServicio;

     /**
     *Atributo que representa el nombre del dueño de un producto
     */
    private String duenio;

     /**
     *Atributo que representa la relacion muchos a uno con la clase ProveedorEntity
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private ProveedorEntity proveedor;
    
     /**
     *Atributo que representa el costo de un producto
     */
    private int costo;

    /**
     *Atributo que representa la cantidad de un producto
     */
    private int cantidad;

     /**
     *Atributo que representa la relacion muchos a muchos con la clase EventoEntity
     */
    @PodamExclude
    @ManyToMany()
    private Collection<EventoEntity> eventos = new ArrayList<EventoEntity>();

    /**
     * Metodo que retorna el nombre de un producto
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que modifica el nombre de un producto
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que retorna el tipo de servicio de un producto
     * @return tipoServicio
     */
    public String getTipoServicio() {
        return tipoServicio;
    }

    /**
     * Metodo que modifica el tipo de servicio de un producto
     * @param tipoServicio 
     */
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    /**
     * Metodo que retorna el nombre del dueño de un producto
     * @return duenio
     */
    public String getDuenio() {
        return duenio;
    }

    /**
     * Metodo que modifica el dueño de un producto
     * @param duenio 
     */
    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    /**
     * Metodo que retorna el proveedor de un producto
     * @return proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * Metodo que modifica el proveedor de un producto
     * @param proveedor 
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Metodo que retorna el costo de un producto
     * @return costo
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Metodo que modifica el costo de un producto
     * @param costo 
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Metodo que retorna la cantidad de un producto
     * @return cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Metodo que modifica la cantidad de un producto
     * @param cantidad 
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Metodo que retorna los eventos de un producto
     * @return eventos
     */
    public Collection<EventoEntity> getEventos() {
        return eventos;
    }

    /**
     * Metodo que modifica los eventos de un producto
     * @param eventos 
     */
    public void setEventos(Collection<EventoEntity> eventos) {
        this.eventos = eventos;
    }

    /**
     * Metodo que agrega un evento a la lista de eventos
     * @param evento 
     */
    public void setEvento(EventoEntity evento) {
        if (eventos == null) {
            eventos = new ArrayList<>();
        }
        eventos.add(evento);
    }
    

}
