/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Entity
public class SugerenciaEntity extends BaseEntity implements Serializable {

    
    private String comentario;
    
    @ManyToOne
    private ClienteEntity cliente;
    
    @ManyToOne
    private TematicaEntity tematica;

    public SugerenciaEntity(){
        
    }
    
    /**
     * Devuelve el comentario de la sugerencia.
     *
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la sugerencia.
     *
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
