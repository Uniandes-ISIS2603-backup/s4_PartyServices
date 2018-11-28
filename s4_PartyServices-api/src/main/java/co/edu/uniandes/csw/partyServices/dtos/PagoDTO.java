/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PagoDTO Objeto de transferencia de datos de pagos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "codigoSeguridadTarjeta": int,
 * "empresa": string,
 * "fechaExpiracionTarjetaCredito": string,
 * "id": long,
 * "nombreTarjeta": string,
 * "numeroTarjetaCredito": long
 * }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 *   {
 * "codigoSeguridadTarjeta": 123,
 * "empresa": "MasterCard",
 * "fechaExpiracionTarjetaCredito": "11/21",
 * "id": 2,
 * "nombreTarjeta": "LAURA FABIO",
 * "numeroTarjetaCredito": 5555555555554444
 *
 * }
 *
 * </pre>
 *
 * @author Elias Negrete
 */
public class PagoDTO implements Serializable {

    /*
    * id del pago 
     */
    private Long id;

    /*
    * fecha del pago.
     */
    private String fecha;

    /*
    * valor del pago.
     */
    private Integer valor;

    /*
    * Relación a un evento
    dado que esta tiene cardinalidad 1.
     */
    private EventoDTO evento;

    /**
     * Constructor por defecto
     */
    public PagoDTO() {
    }

    /**
     * Constructor a partir de una entidad
     *
     * @param pagoEntity La entidad de la cual se construye el DTO
     */
    public PagoDTO(PagoEntity pagoEntity) {
        if (pagoEntity != null) {
            this.id = pagoEntity.getId();
            this.fecha = pagoEntity.getFecha();
            this.valor = pagoEntity.getValor();
            if (pagoEntity.getEvento() != null) {
                this.evento = new EventoDTO(pagoEntity.getEvento());
            } else {
                this.evento = null;
            }
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PagoEntity toEntity() {
        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setId(this.id);
        pagoEntity.setFecha(this.fecha);
        pagoEntity.setValor(this.valor);
        if (this.evento != null) {
            pagoEntity.setEvento(this.evento.toEntity());
        }

        return pagoEntity;
    }

    /**
     * Devuelve el ID.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    
    
    
    /**
     * Modifica el ID del pago.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    

    public String getFecha() {
        return fecha;
    }

    
    
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    public Integer getValor() {
        return valor;
    }

    
    
    
    public void setValor(Integer valor) {
        this.valor = valor;
    }

    
    
    
    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    
    

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}