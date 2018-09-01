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


import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class EventoDTO implements Serializable
{
    
    private Long id;
    private String estado;
    private Integer fecha;
    
    /**
     * Constructor por defecto.
     */
    public EventoDTO() {
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
    public Integer getFecha() {
        return fecha;
    }

    /**
     * Modifica la fecha del evento
     * @param fecha 
     */
    public void setFecha(Integer fecha) {
        this.fecha = fecha;
    }
    
   
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
