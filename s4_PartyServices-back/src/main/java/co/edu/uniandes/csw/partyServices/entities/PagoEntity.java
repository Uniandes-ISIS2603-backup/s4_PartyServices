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
 * @author  Elias Negrete
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable{
    
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
    
    public PagoEntity()
    {
        
    }
    /**
     * Devuelve el cliente de la reseña.
     *
     * @return the name
     */
     public ClienteEntity getCliente() {
        return cliente;
    }

      /**
     * Modifica el cliente asociado a este libro
     *
     * @param pCliente  El nuevo cliente
     */
    public void setCliente(ClienteEntity pCliente) {
        this.cliente = pCliente;
    }
    
    /**
     * Devuelve el login del cliente.
     *
     * @return the user
     */
    public String getUsuario(){
        return usuario;
    }
    
     /**
     * Modifica el usuario
     *
     * @param pUsuario  El nuevo user
     */
    public void setUsuario(String pUsuario){
        this.usuario = pUsuario;
    }
    
    /**
     * Devuelve la tarjeta del cliente.
     *
     * @return the number
     */
    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

     /**
     * Modifica el numero de tarjeta
     *
     * @param numeroTarjetaCredito   El nuevo numero
     */
    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

     /**
     * Devuelve el codigo de seguridad  de a tarjeta.
     *
     * @return the code
     */
    public Integer getCodigoSeguridadTarjeta() {
        return codigoSeguridadTarjeta;
    }

     /**
     * Modifica el codigo de tarjeta
     *
     * @param codigoSeguridadTarjeta   El nuevo codigo
     */
    public void setCodigoSeguridadTarjeta(Integer codigoSeguridadTarjeta) {
        this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
    }

     /**
     * Devuelve la fecah de expiracion de la tarjeta
     *
     * @return the date
     */
    public String getFechaExpiracionTarjetaCredito() {
        return fechaExpiracionTarjetaCredito;
    }

     /**
     * Modifica la fecha de expiración
     *
     * @param fechaExpiracionTarjetaCredito   El nuevo date
     */
    public void setFechaExpiracionTarjetaCredito(String fechaExpiracionTarjetaCredito) {
        this.fechaExpiracionTarjetaCredito = fechaExpiracionTarjetaCredito;
    }

     /**
     * Devuelve el nombre en la tarjeta
     *
     * @return the name
     */
    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    
     /**
     * Modifica el nombre de tarjeta
     *
     * @param nombreTarjeta   El nuevo nombre
     */
    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

     /**
     * Devuelve la empresa de la tarjeta
     *
     * @return the bank
     */
    public String getEmpresa() {
        return empresa;
    }

     /**
     * Modifica la empresa d ela tarjeta
     *
     * @param empresa   El nuevo bank
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    
    

    
}
