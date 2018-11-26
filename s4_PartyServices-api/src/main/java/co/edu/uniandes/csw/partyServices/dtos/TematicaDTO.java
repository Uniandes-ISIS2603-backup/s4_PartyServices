/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

/**
Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string
 *   }
 * </pre> Por ejemplo una tematica se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "nombre": "Infantil"
 *   }
 *
 * </pre>

/**
 *
 * @author Tomas Vargas 
 */


import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Tomas Vargas
 */
public class TematicaDTO implements Serializable{
    
     private Long id;
    private String name;
    
    /**
     * Constructor por defecto.
     */
    public TematicaDTO() {
    }

     /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param tematicaEntity: Es la entidad que se va a convertir a DTO
     */
     
    public TematicaDTO(TematicaEntity tematicaEntity) {
        if (tematicaEntity != null) {
            this.id = tematicaEntity.getId();
            this.name = tematicaEntity.getName();
        }
    }
    
    
    /**
     * Devuelve el id de la tematica
     * @return id de la tematica
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el id de la tematica 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre actual de la tematica
     * @return nombre de la tematica
     */
    public String getName() {
        return name;
    }
    
    /**
     * Modifica el nombre de la tematica
     * @param name  
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
     
    public TematicaEntity toEntity() {
        TematicaEntity tematicaEntity = new TematicaEntity();
        tematicaEntity.setId(this.id);
        tematicaEntity.setName(this.name);
        return tematicaEntity;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
