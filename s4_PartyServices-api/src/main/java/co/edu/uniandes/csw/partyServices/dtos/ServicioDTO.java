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
 *      "tipo": string
 *   }
 * </pre> Por ejemplo un servicio se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "tipo": "Infantil"
 *   }
 *
 * </pre>

/**
 *
 * @author Tomas Vargas 
 */

import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Tomas Vargas
 */
public class ServicioDTO implements Serializable
{
    
  private Long id;
  private String tipo;


public ServicioDTO(){
    
}

/**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param servicioEntity: Es la entidad que se va a convertir a DTO
     */
     
    public ServicioDTO(ServicioEntity servicioEntity) {
        if (servicioEntity != null) {
            this.id = servicioEntity.getId();
            this.tipo = servicioEntity.getTipo();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

/**
     * Convierte un objeto ServicioDTO a ServicioEntity.
     *
     * @return Nueva objeto ServicioEntity.
     *
     */
    public ServicioEntity toEntity() {
        ServicioEntity servicioEntity = new ServicioEntity();
        servicioEntity.setId(this.getId());
        servicioEntity.setTipo(this.getTipo());
        return servicioEntity;
    }


}
