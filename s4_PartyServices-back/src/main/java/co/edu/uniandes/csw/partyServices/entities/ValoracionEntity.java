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
 * @author  Jesús Orlando Cárcamo Posada
 */
@Entity
public class ValoracionEntity extends BaseEntity implements Serializable{
    
    private Integer puntaje;
    
    private String comentario;
    
    private String nombreUsuario;
    
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    public ValoracionEntity()
    {
        
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }
  
    /**
     * Devuelve el puntaje de la valoracion.
     * 
     * @return puntaje.
     */
    public Integer getPuntaje(){
        return puntaje;
    }
    
    /**
     * Modifica el puntaje de la valoracion.
     *
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(Integer puntaje){
        this.puntaje = puntaje;
    }
    
    
    /**
     * Devuelve el comentario de la valoracion.
     *
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la valoracion.
     *
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    /**
     * Devuelve el nombre del usuario de la valoracion.
     *
     * @return nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el nombre del usuario de la valoracion.
     *
     * @param nombreUsuario el nuevo nombre del usuario que se reemplazará.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
