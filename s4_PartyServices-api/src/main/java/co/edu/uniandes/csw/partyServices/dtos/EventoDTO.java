/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Andres
 */
public class EventoDTO implements Serializable {

    /**
     * Id del evento
     */
    private Long id;

    /**
     * Nombre del evento
     */
    private String nombre;

    /**
     * Estado del evento
     */
    private String estado;

    /**
     * Fecha del evento
     */
    private FechaDTO fecha;

    /**
     * Cliente del evento
     */
    private ClienteDTO cliente;

    /**
     * Latitud en la que se realizará el evento
     */
    private double latitud;

    /**
     * Longitud en la que se realizará el evento
     */
    private double longitud;

    /**
     * Constructor por defecto.
     */
    public EventoDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param eventoEntity Es la entidad que se va a convertir a DTO
     */
    public EventoDTO(EventoEntity eventoEntity) {
        if (eventoEntity != null) {
            this.id = eventoEntity.getId();
            this.nombre = eventoEntity.getNombre();
            this.estado = eventoEntity.getEstado();
//            this.fecha = new FechaDTO(eventoEntity.getFecha());
            if (this.cliente != null) {
            this.cliente = new ClienteDTO(eventoEntity.getCliente());
            }
            this.latitud = eventoEntity.getLatitud();
            this.longitud = eventoEntity.getLongitud();

        }
    }

    /**
     * Convierte el DTO actual a un entity
     *
     * @return un objeto Entity de evento
     */
    public EventoEntity toEntity() {
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId(this.getId());
        eventoEntity.setNombre(this.getNombre());
        eventoEntity.setEstado(this.estado);
        //eventoEntity.setFecha(this.fecha.toEntity());
        if (this.cliente != null) {
        eventoEntity.setCliente(this.cliente.toEntity());
        }
        eventoEntity.setLatitud(this.latitud);
        eventoEntity.setLongitud(this.longitud);
        return eventoEntity;
    }

    /**
     * Devuelve el id del evento
     *
     * @return id del evento
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id del evento
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el estado actual del evento
     *
     * @return estado del evento
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica el estado del evento
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna la fecha de realización del evento
     *
     * @return fecha del evento
     */
    public FechaDTO getFecha() {
        return fecha;
    }

    /**
     * Modifica la fecha del evento
     *
     * @param fecha
     */

    public void setFecha(FechaDTO fecha) {
        this.fecha = fecha;
    }

    /**
     * Retorna el cliente del evento
     *
     * @return cliente
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Modifica el cliente del evento
     *
     * @param cliente
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna la latitud del evento
     *
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Modifica la latidud del evento y le asigna la del argumento
     *
     * @param latitud
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Retorna la longitud del evento
     *
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Modifica la longitud del evento
     *
     * @param longitud
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Metodo ToString
     *
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
