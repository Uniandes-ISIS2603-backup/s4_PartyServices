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
public class ValoracionEntity extends BaseEntity implements Serializable {

    private String comentario;

    private Integer puntaje;

    private String nombreUsuario;
    
    private String titulo;

    


    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;

    /**
     * Constructor por defecto para crear una ValoracionEntity
     */
    public ValoracionEntity() {
        //Constructor por defecto para crear una ValoracionEntity
    }

    /**
     * Devuelve el comentario de la valoracion.
     *
     * @return comentario. El comentario hecho por un usuario de la valoracion.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la valoracion.
     *
     * @param pComentario. El nuevo comentario que se le asiganará a la
     * valoracion.
     */
    public void setComentario(String pComentario) {
        comentario = pComentario;
    }

    /**
     * Devuelve el puntaje de la valoracion.
     *
     * @return puntaje. El puntaje dado en la valoracion.
     */
    public Integer getPuntaje() {
        return puntaje;
    }

    /**
     * Modifica el puntaje de la valoracion.
     *
     * @param pPuntaje. El nuevo puntaje de la valoración.
     */
    public void setPuntaje(Integer pPuntaje) {
        puntaje = pPuntaje;
    }

    /**
     * Devuelve el nombre del usuario que creó la valoración.
     *
     * @return nombreUsuario. El nombre del usuario que creó la valroación.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el nombre del usuario asociado a la valoración.
     *
     * @param pNombreUsuario. El nuevo nombre del usuario que se reemplazará.
     */
    public void setNombreUsuario(String pNombreUsuario) {
        nombreUsuario = pNombreUsuario;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Devuelve el usuario asociado a la valoración. Null si la valoración no
     * tiene usuario.
     *
     * @return cliente. La entidad de cliente asociado a la valoración.
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * Modifica el cliente asociado a la valoración.
     *
     * @param pCliente. El nuevo cliente que será dueño de la valoración.
     */
    public void setCliente(ClienteEntity pCliente) {
        cliente = pCliente;
    }

    /**
     * Devuelve el proveedor al cual se le hizo la valoración.
     *
     * @return proveedor. El proveedor que tiene esta valroación.
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * Modifica el proveedor de esta valroación.
     *
     * @param pProveedor. El nuevo proveedor que será dueño de la valroación.
     */
    public void setProveedor(ProveedorEntity pProveedor) {
        proveedor = pProveedor;
    }
}
