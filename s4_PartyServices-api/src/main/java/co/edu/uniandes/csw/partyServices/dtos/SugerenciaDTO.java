/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SugerenciaDTO Objeto de transferencia de datos de Sugerencias.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "titulo":string,
 *      "id": number,
 *      "comentario": string
 *   }
 * </pre> Por ejemplo una sugerencia se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "titulo":"Esta fiesta estuvo genial"
 *      "comentario": "Imprescindible pastel de arequipe"
 *   }
 *
 * </pre>
 *
 * @author Jesús Orlando Cárcamo Posada
 */
public class SugerenciaDTO implements Serializable {

    private Long id;
    private String comentario;
    private String titulo;
    private String nombreUsuario;
    private String link;

    private TematicaDTO tematica;

    

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    private ClienteDTO cliente;
    /**
     * Constructor por defecto
     */
    public SugerenciaDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param sugerenciaEntity: Es la entidad que se va a convertir a DTO
     */
    public SugerenciaDTO(SugerenciaEntity sugerenciaEntity) {
        if (sugerenciaEntity != null) {
            this.id = sugerenciaEntity.getId();
            this.comentario = sugerenciaEntity.getComentario();
            this.nombreUsuario = sugerenciaEntity.getNombreUsuario();
            this.link = sugerenciaEntity.getLink();
            this.titulo = sugerenciaEntity.getTitulo();
            if (sugerenciaEntity.getTematica()!= null) {
                this.tematica = new TematicaDTO(sugerenciaEntity.getTematica());
            } else {
                this.tematica = null;
            }
            if (sugerenciaEntity.getCliente() != null) {
                this.cliente = new ClienteDTO(sugerenciaEntity.getCliente());
            } else {
                this.cliente = null;
            }
        }
    }

    /**
     * Devuelve el ID de la sugerencia.
     *
     * @return id. El ID de la sugerencia.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la sugerencia.
     *
     * @param id. El id nuevo ID de la sugerencia.
     */
    public void setId(Long id) {
        this.id = id;
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
     * @param comentario. El nuevo comentario que se asignará a la sugerencia.
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
     * Devuelve el titulo asignado a la sugerencia.
     *
     * @return titulo. El titulo del usuario asignado a la sugerencia.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Modifica el titulo asignado a la sugerencia.
     *
     * @param pTitulo. El nuevo titulo de la sugerencia.
     */
    public void setTitulo(String pTitulo) {
        this.titulo = pTitulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public TematicaDTO getTematica() {
        return tematica;
    }

    public void setTematica(TematicaDTO tematica) {
        this.tematica = tematica;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SugerenciaEntity toEntity() {
        SugerenciaEntity sugerenciaEntity = new SugerenciaEntity();
        sugerenciaEntity.setId(this.id);
        sugerenciaEntity.setComentario(this.comentario);
        sugerenciaEntity.setNombreUsuario(this.nombreUsuario);
        sugerenciaEntity.setLink(this.link);
        sugerenciaEntity.setTitulo(this.titulo);

         if (this.tematica != null) {
            sugerenciaEntity.setTematica(this.tematica.toEntity());
        }
         if (this.cliente != null) {
            sugerenciaEntity.setCliente(this.cliente.toEntity());
        }
        return sugerenciaEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
