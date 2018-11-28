/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Objects;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Hash
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.comentario);
        hash = 23 * hash + Objects.hashCode(this.nombreUsuario);
        return hash;
    }

    /**
     * Equals
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SugerenciaEntity other = (SugerenciaEntity) obj;
        if (!Objects.equals(this.comentario, other.comentario)) {
            return false;
        }
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.tematica, other.tematica)) {
            return false;
        }
        return true;
    }

}
