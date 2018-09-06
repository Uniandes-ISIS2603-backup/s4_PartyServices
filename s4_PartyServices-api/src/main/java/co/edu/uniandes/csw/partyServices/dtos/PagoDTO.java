/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

//import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PagoDTO Objeto de transferencia de datos de pagos.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "tipo": string,
 *      "usuario": string
 *   }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "tipo": "Credito Bancolombia",
 *      "usuario": "dark"
 *   }
 *
 * </pre>
 * @author Elias Negrete
 */
public class PagoDTO implements Serializable{
    
    private Long id;
    private String tipo;
    private String usuario;

    
    /**
     * Constructor por defecto
     */
    public PagoDTO() {
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param pagoEntity: Es la entidad que se va a convertir a DTO
     */
    /*public PagoDTO(PagoEntity pagoEntity) {
        if (pagoEntity != null) {
            this.id = pagoEntity.getId();
            this.tipo = sugerenciaEntity.getTipo();
        }
    }*/
    
    
    /**
     * Devuelve el ID.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    
     public String getUsuario() {
        return usuario;
    }
     public void setUsuario(String pUsuario) {
        this.usuario = pUsuario;
        
    }
    
    
    /**
     * Modifica el ID del pago.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Devuelve el tipo del pago.
     *
     * @return el  tipo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del pago.
     *
     * @param pago1 pago
     */
    public void setTipo(String pago1) {
        this.tipo = pago1;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    /*public PagoEntity toEntity() {
        PagoEntity pagoEntity = new PagoEntity();
        PagoEntity.setId(this.id);
        PagoEntity.setTipo(this.tipo);
        return pagoEntity;
    }*/
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
