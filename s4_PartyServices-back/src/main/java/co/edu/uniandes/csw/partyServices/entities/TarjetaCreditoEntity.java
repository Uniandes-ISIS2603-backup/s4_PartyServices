/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Entity
public class TarjetaCreditoEntity extends BaseEntity implements Serializable{
    
    /*
    * Nombre del titular de la tarjeta.
     */
    private String nombreTitular;

    /*
    * Número de la tarjeta de credito 
     */
    private Long numero;

    /*
    * Código de seguridad de la tarjeta.
     */
    private Integer codigoSeguridad;

    /*
    * fecha de expiracion de la tarjeta 
     */
    
    private String fechaExpiracion;

    /*
    * Nombre del banco de la tarjeta.
     */
    private String banco;

    /*
    * Nombre de la franquicia de la tarjeta.
     */
    private String franquicia;
    
    /**
     * Relacion uno a uno con el cliente de la tarjeta de credito.
     */
    @PodamExclude
    @OneToOne( )
    private ClienteEntity cliente;
    
    /**
     * constructor base de la clase
     */
    public TarjetaCreditoEntity() {

        //constructor base de la TarjetaCreditoEntity
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
    
    
}
