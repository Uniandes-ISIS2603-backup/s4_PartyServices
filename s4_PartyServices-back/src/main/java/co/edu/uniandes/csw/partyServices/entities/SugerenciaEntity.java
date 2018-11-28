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
 * @author Jesús Orlando Cárcamo Posada
 */
@Entity
public class SugerenciaEntity extends BaseEntity implements Serializable {

    private String comentario;

    private String nombreUsuario;
    
    private String titulo;
    
    private String link;
    
    private String video;

    

    
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    @PodamExclude
    @ManyToOne
    private TematicaEntity tematica;
    

    /**
     * Constructor por defecto de una SugerenciaEntity.
     */
    public SugerenciaEntity() {
        //Constructor por defecto de una SugerenciaEntity.
    }

    /**
     * Devuelve el comentario de la sugerencia.
     *
     * @return comentario. El comentario de la sugerencia.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la sugerencia.
     *
     * @param comentario. El nuevo comentario que se reemplazará.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Devuelve el nombre del usuario asignado a la sugerencia.
     *
     * @return nombreUsuario. El nombre del usuario asignado a la sugerencia.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el nombre del usuario asignado a la sugerencia.
     *
     * @param nombreUsuario. El nuevo nombre del usuario que se reemplazará.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Devuelve el usuario asignado a la sugerencia.
     *
     * @return cliente. El cliente asignado a la sugerencia.
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * Modifica el usuario asignado a la sugerencia.
     *
     * @param cliente. El nuevo cliente que se reemplazará.
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve la temática de la que hace parte la sugerencia.
     *
     * @return tematica. La temática de la sugerencia.
     */
    public TematicaEntity getTematica() {
        return tematica;
    }

    /**
     * Modifica la temática de la que hace parte la sugerencia.
     *
     * @param tematica. La nueva temática de la que hará parte la sugerencia.
     */
    public void setTematica(TematicaEntity tematica) {
        this.tematica = tematica;
    }
    
    /**
     * Agarra el titulo de sugerencia
     * @return el titulo de la sugerencia 
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Cambia el titulo de la sugerencia
     * @param titulo. El titulo a ser cambiado
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Agarra el link de sugerencia
     * @return el link de la sugerencia
     */
    public String getLink() {
        return link;
    }

    /**
     * Cambia el link de la sugerencia.
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }
    
   
    /**
     * Obtiene el video de la sugerencia
     * @return el video de la sugerencia
     */
    public String getVideo() {
        return video;
    }

    /**
     * Modifica el video de la sugerencia
     * @param video. Video de la sugerencia. 
     */
    public void setVideo(String video) {
        this.video = video;
    }

    
    

}
