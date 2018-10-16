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
    * usuario al que pertenece el pago 
     */
    private String usuario;

    /*
    * numero del tarjeta de credito 
     */
    private Long numeroTarjetaCredito;

    /*
    * codigo de seguridad 
     */
    private Integer codigoSeguridadTarjeta;

    /*
    * fecha de expiracion de la tarjeta 
     */
    private String fechaExpiracionTarjetaCredito;

    /*
    *nombre del dueño de la tarjeta
     */
    private String nombreTarjeta;

    /*
    * empresa que expide la tarjeta 
     */
    private String empresa;

    /*
    * Relación a un CLIENTE
    * dado que ésta tiene cardinalidad 1.
     */
    private ClienteDTO cliente;

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
            this.codigoSeguridadTarjeta = pagoEntity.getCodigoSeguridadTarjeta();
            this.empresa = pagoEntity.getEmpresa();
            this.fechaExpiracionTarjetaCredito = pagoEntity.getFechaExpiracionTarjetaCredito();
            this.nombreTarjeta = pagoEntity.getNombreTarjeta();
            this.numeroTarjetaCredito = pagoEntity.getNumeroTarjetaCredito();
            if (pagoEntity.getCliente() != null) {
                this.cliente = new ClienteDTO(pagoEntity.getCliente());
            } else {
                this.cliente = null;
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
        pagoEntity.setCodigoSeguridadTarjeta(this.codigoSeguridadTarjeta);
        pagoEntity.setEmpresa(this.empresa);
        pagoEntity.setFechaExpiracionTarjetaCredito(this.fechaExpiracionTarjetaCredito);
        pagoEntity.setNombreTarjeta(this.nombreTarjeta);
        pagoEntity.setNumeroTarjetaCredito(this.numeroTarjetaCredito);
        if (this.cliente != null) {
            pagoEntity.setCliente(this.cliente.toEntity());
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

    /**
     * Devuelve el LOGIN del usuario.
     *
     * @return the user
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Modifica el usuario del pago.
     *
     * @param pUsuario the id to set
     */
    public void setUsuario(String pUsuario) {
        this.usuario = pUsuario;

    }

    /**
     * Devuelve el numero de tarjeta del usuario.
     *
     * @return the card number
     */
    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

    /**
     * Modifica la tarjeta del pago.
     *
     * @param numeroTarjetaCredito the number to set
     */
    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

    /**
     * Devuelve el codigo de la tarjeta.
     *
     * @return the code
     */
    public Integer getCodigoSeguridadTarjeta() {
        return codigoSeguridadTarjeta;
    }

    /**
     * Modifica el codigo de la tarjeta del pago.
     *
     * @param codigoSeguridadTarjeta the code to set
     */
    public void setCodigoSeguridadTarjeta(Integer codigoSeguridadTarjeta) {
        this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
    }

    /**
     * Devuelve la fecha de expiracion de la tarjeta del usuario.
     *
     * @return the expiration date
     */
    public String getFechaExpiracionTarjetaCredito() {
        return fechaExpiracionTarjetaCredito;
    }

    /**
     * Modifica la fecha de expiracion de la tarjeat del pago.
     *
     * @param fechaExpiracionTarjetaCredito the date to set
     */
    public void setFechaExpiracionTarjetaCredito(String fechaExpiracionTarjetaCredito) {
        this.fechaExpiracionTarjetaCredito = fechaExpiracionTarjetaCredito;
    }

    /**
     * Devuelve el nombre en la tarjeta.
     *
     * @return the user
     */
    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    /**
     * Modifica el nombre de tarjeta del pago.
     *
     * @param nombreTarjeta the name to set
     */
    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    /**
     * Devuelve la empresa de tarjeta
     *
     * @return the enterprise
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Modifica la empresa del pago.
     *
     * @param empresa the enterprise to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * Devuelve el CLIENTE.
     *
     * @return the client
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Modifica el cliente del pago.
     *
     * @param cliente the client to set
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
