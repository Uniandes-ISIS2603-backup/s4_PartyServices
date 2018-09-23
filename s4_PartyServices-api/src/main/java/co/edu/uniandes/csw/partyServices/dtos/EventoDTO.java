/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;


/**
 *
 * @author Andres
 */


import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class EventoDTO implements Serializable
{
    
    private Long id;
    private String nombre ;
    private String estado;
    private FechaDTO fecha;
    private ClienteDTO cliente ;
    private double latitud ;
    private double longitud ;
    
    /**
     * Constructor por defecto.
     */
    public EventoDTO() {
    }

    public EventoDTO(EventoEntity eventoEntity) {
        if (eventoEntity != null) 
        {
            this.id = eventoEntity.getId();
            this.nombre = eventoEntity.getNombre();
            this.estado = eventoEntity.getEstado() ;
            this.fecha = new FechaDTO(eventoEntity.getFecha()) ;
            this.cliente = new ClienteDTO(eventoEntity.getCliente());
            this.latitud = eventoEntity.getLatitud();
            this.longitud = eventoEntity.getLongitud() ;
  
        }
    }
    
      public EventoEntity toEntity() {
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId(this.getId());
        eventoEntity.setNombre(this.getNombre());
        eventoEntity.setEstado(this.estado);
        eventoEntity.setFecha(this.fecha.toEntity());
        eventoEntity.setCliente(this.cliente.toEntity());
        eventoEntity.setLatitud(this.latitud);
        eventoEntity.setLongitud(this.longitud);
        return eventoEntity;
    }

    
    /**
     * Devuelve el id del evento
     * @return id del evento
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el id del evento 
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
     * @return estado del evento
     */
    public String getEstado() {
        return estado;
    }
    
    /**
     * Modifica el estado del evento
     * @param estado 
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

   
     /**
     * Retorna la fecha de realización del evento
     * @return fecha del evento
     */
   
    public FechaDTO getFecha() {
        return fecha;
    }
/**
     * Modifica la fecha del evento
     * @param fecha 
     */
   
    public void setFecha(FechaDTO fecha) {   
        this.fecha = fecha;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
