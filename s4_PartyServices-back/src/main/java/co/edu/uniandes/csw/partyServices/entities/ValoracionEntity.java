/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author  Jesús Orlando Cárcamo Posada
 */
@Entity
public class ValoracionEntity extends BaseEntity implements Serializable{
    
    private Integer puntaje;
    
    private String comentario;
    
    /*@javax.persistence.ManyToOne
    private ClienteEntity cliente;*/
    
    /*@javax.persistence.ManyToOne
    private ProveedorEntity proveedor;*/
    
    public ValoracionEntity()
    {
        
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
}
