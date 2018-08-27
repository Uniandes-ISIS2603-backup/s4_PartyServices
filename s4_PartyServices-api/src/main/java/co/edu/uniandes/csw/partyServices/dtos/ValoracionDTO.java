/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SugerenciaDTO Objeto de transferencia de datos de Valoraciones.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "puntaje": number
 *      "comentario": string
 *   }
 * </pre> Por ejemplo una sugerencia se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "puntaje": 5
 *      "comentario": "Globos de mala calidad"
 *   }
 *
 * </pre>
 * @author Jesús Orlando Cárcamo Posada
 */
public class ValoracionDTO implements Serializable{
    
    private Long id;
    private byte puntaje;
    private String comentario;
    
    
    /**
     * Constructor por defecto
     */
    public ValoracionDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param valoracionEntity: Es la entidad que se va a convertir a DTO
     */
    public ValoracionDTO(ValoracionEntity valoracionEntity) {
        if (valoracionEntity != null) {
            this.id = valoracionEntity.getId();
            this.puntaje = valoracionEntity.getPuntaje();
            this.comentario = valoracionEntity.getComentario();
        }
    }
    
    /**
     * Devuelve el ID de la valoracion.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el ID de la valoracion.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Devuelve el puntaje de la valoracion.
     *
     * @return el puntaje de la valoracion.
     */
    public byte getPuntaje() {
        return puntaje;
    }
    
    /**
     * Modifica el puntaje de la valoracion.
     *
     * @param puntaje the comentario to set
     */
    public void setPuntaje(byte puntaje) {
        this.puntaje = puntaje;
    }
    
    /**
     * Devuelve el comentario de la valoracion.
     *
     * @return el  comentario de la valoracion.
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
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ValoracionEntity toEntity() {
        ValoracionEntity valoracionEntity = new ValoracionEntity();
        valoracionEntity.setId(this.id);
        valoracionEntity.setPuntaje(this.puntaje);
        valoracionEntity.setComentario(this.comentario);
        return valoracionEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
