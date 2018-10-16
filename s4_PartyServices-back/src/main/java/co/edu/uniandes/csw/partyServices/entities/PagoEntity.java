/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un pago en la persistencia y permite su serializacion
 *
 * @author Elias Negrete
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    /*
    *login del cliente
     */
    private String usuario;

    /*
    *tarjeta de credito del cliente
     */
    private Long numeroTarjetaCredito;

    /*
    *codigo de seguridad de la tarjeta
     */
    private Integer codigoSeguridadTarjeta;

    
    /*
    *fecha de expiracion d ela tarjeta
     */
    private String fechaExpiracionTarjetaCredito;

    /*
    *nombre en la tarjeta
     */
    private String nombreTarjeta;

    /*
    *empresa de la tarjeta 
     */
    private String empresa;

    /*
    *cliente que guarda al pago
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ClienteEntity cliente;

    /**
     * Constructor por defecto
     */
    public PagoEntity() {

        //constructor por defecto
    }

    /**
     * Método que retorna el nombre de usuario de su cliente
     * @return usuario del cliente quien contiene este pago
     */
    public String getUsuarioEntity() {
        return usuario;
    }

    /**
     * Método que edita al usuario
     * @param pUsuario 
     */
    public void setUsuarioEntity(String pUsuario) {
        this.usuario = pUsuario;
    }
 
    /**
     * Método que retorna el número de la tarjeta
     * @return el número de la tarjeta
     */
    public Long getNumeroTarjetaCreditoEntity() {
        return numeroTarjetaCredito;
    }

    /**
     * Método que edita a la tarjeta de crédito
     * @param pNumeroTarjetaCredito 
     */
    public void setNumeroTarjetaCreditoEntity(Long pNumeroTarjetaCredito) {
        this.numeroTarjetaCredito = pNumeroTarjetaCredito;
    }

    /**
     * Método que retorna el código de seguridad
     * @return código de seguridad de la tarjeta
     */
    public Integer getCodigoSeguridadTarjetaEntity() {
        return codigoSeguridadTarjeta;
    }

    /**
     * Método que edita el código de seguridad
     * @param pCodigoSeguridadTarjeta 
     */
    public void setCodigoSeguridadTarjetaEntity(Integer pCodigoSeguridadTarjeta) {
        this.codigoSeguridadTarjeta = pCodigoSeguridadTarjeta;
    }

    /**
     * Método que retorna la fecha de expiracion
     * @return fecha en que la tarjeta expira
     */
    public String getFechaExpiracionTarjetaCreditoEntity() {
        return fechaExpiracionTarjetaCredito;
    }

    /**
     * Método que edita la fecha de expiración
     * @param pFechaExpiracionTarjetaCredito 
     */
    public void setFechaExpiracionTarjetaCreditoEntity(String pFechaExpiracionTarjetaCredito) {
        this.fechaExpiracionTarjetaCredito = pFechaExpiracionTarjetaCredito;
    }

    /**
     * Método que retorno el nombre del dueño
     * @return nombre del dueño de la tarjeta
     */
    public String getNombreTarjetaEntity() {
        return nombreTarjeta;
    }

    /**
     * Método que edita el nombre de la tarjeta
     * @param pNombreTarjeta 
     */
    public void setNombreTarjetaEntity(String pNombreTarjeta) {
        this.nombreTarjeta = pNombreTarjeta;
    }

    /**
     * Método que retorno la empresa
     * @return la empresa de la tarjeta de crédito
     */
    public String getEmpresaEntity() {
        return empresa;
    }

    /**
     * Método que edita la empresa bancaria de la tarjeta
     * @param pEmpresa 
     */
    public void setEmpresaEntity(String pEmpresa) {
        this.empresa = pEmpresa;
    }

    /**
     * Método que retorno el cliente
     * @return cliente que contiene este pago
     */
    public ClienteEntity getClienteEntity() {
        return cliente;
    }

    /**
     * Método que edita el cliente al que le pertenece este pago
     * @param cliente 
     */
    public void setClienteEntity(ClienteEntity cliente) {
        this.cliente = cliente;
    }

   

}
