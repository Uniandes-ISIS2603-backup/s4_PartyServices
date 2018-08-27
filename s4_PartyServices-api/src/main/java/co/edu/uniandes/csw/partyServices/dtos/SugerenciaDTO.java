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
 *      "id": number,
 *      "comentario": string
 *   }
 * </pre> Por ejemplo una sugerencia se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "comentario": "Imprescindible pastel de arequipe"
 *   }
 *
 * </pre>
 * @author Jesús Orlando Cárcamo Posada
 */
public class SugerenciaDTO implements Serializable{
    
    private Long id;
    private String comentario;
    
    
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
        }
    }
    
    /**
     * Devuelve el ID de la sugerencia.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el ID de la sugerencia.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Devuelve el comentario de la sugerencia.
     *
     * @return el  comentario de la sugerencia.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la sugerencia.
     *
     * @param name the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        return sugerenciaEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
