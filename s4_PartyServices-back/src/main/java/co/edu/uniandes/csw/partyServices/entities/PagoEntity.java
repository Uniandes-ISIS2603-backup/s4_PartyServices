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

    
    public void setTipo(String pTipo) {
        this.tipo = pTipo;
    }
}
