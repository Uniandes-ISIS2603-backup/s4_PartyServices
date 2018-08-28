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


import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Tomas Vargas
 */
public class TematicaDTO {
    
     private Long id;
    private String nombre;
    
    /**
     * Constructor por defecto.
     */
    public TematicaDTO() {
    }

     /**
      * 
      * SE DEBE IMPLEMENTAR CUANDO SE TENGAN LAS ENTIDADES
      * 
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param editorialEntity: Es la entidad que se va a convertir a DTO
     
    public EditorialDTO(EditorialEntity editorialEntity) {
        if (editorialEntity != null) {
            this.id = editorialEntity.getId();
            this.name = editorialEntity.getName();
        }
    }
    */
    
    /**
     * Devuelve el id del evento
     * @return id del evento
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el id del evento 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre actual del evento
     * @return estado del evento
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Modifica el nombre del evento
     * @param estado 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * 
     * SE DEBE IMPLEMENTAR CUANDO SE TENGAN LOS ENTITY
     * 
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     
    public EditorialEntity toEntity() {
        EditorialEntity editorialEntity = new EditorialEntity();
        editorialEntity.setId(this.id);
        editorialEntity.setName(this.name);
        return editorialEntity;
    }
    */
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
