/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author  Elias Negrete
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable{
    
    private String usuario;
    private String tipo;
    private Long numeroTarjetaCredito;
    private Integer codigoSeguridadTarjeta;
    private String fechaExpiracionTarjetaCredito;
    private String nombreTarjeta;
    private String empresa;
    private boolean cancelado;

    

    
    @PodamExclude
    @ManyToOne()
    private ClienteEntity cliente;
    
    public PagoEntity()
    {
        
    }
     public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity pCliente) {
        this.cliente = pCliente;
    }
    
    
    public String getUsuario(){
        return usuario;
    }
    
    public void setUsuario(String pUsuario){
        this.usuario = pUsuario;
    }
    
    public String getTipo() {
        return tipo;
    }
    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

    public Integer getCodigoSeguridadTarjeta() {
        return codigoSeguridadTarjeta;
    }

    public void setCodigoSeguridadTarjeta(Integer codigoSeguridadTarjeta) {
        this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
    }

    public String getFechaExpiracionTarjetaCredito() {
        return fechaExpiracionTarjetaCredito;
    }

    public void setFechaExpiracionTarjetaCredito(String fechaExpiracionTarjetaCredito) {
        this.fechaExpiracionTarjetaCredito = fechaExpiracionTarjetaCredito;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    
    public void setTipo(String pTipo) {
        this.tipo = pTipo;
    }
    
    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    
}
