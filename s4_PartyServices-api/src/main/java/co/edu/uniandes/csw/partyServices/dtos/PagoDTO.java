/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

//import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
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
    //long que modela el id de un pago
    private Long id;
    
    //string que representa el tipo de un usuario
    private String tipo;
    
    //datos de un usuario
    private String usuario;
    
    //medio de pago
    private String medioPago;
    
    
   
    
    
    

    
    public final static String TIPO_TARJETA_CREDITO = "Tarjeta";
    public final static String TIPO_PAYPAL= "Paypal";
    public final static String TIPO_ESP = "ESP";

    

    
    /**
     * Constructor por defecto
     */
    public PagoDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento).
     *
     * @param pagoEntity: Es la entidad que se va a convertir a DTO
     */
    public PagoDTO(PagoEntity pagoEntity) {
        if (pagoEntity != null) {
            
            this.id = pagoEntity.getId();
            this.usuario = pagoEntity.getUsuario();
            this.tipo = pagoEntity.getTipo();
            this.medioPago = pagoEntity.getMedioPago();

        }
    }
    
    
    
    
    
    /**
     * Devuelve el ID.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
    
    /**
     * Devuelve los datos de un usuario
     *
     * @return the usuario
     */
     public String getUsuario() {
        return usuario;
    }
     
     /**
     * Modifica el ID del pago.
     *
     * @param pUsuario  the usuario to set
     */
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
     * @param pagoTipo pago
     */

    public void setTipo(String pagoTipo) {
        this.tipo = pagoTipo;

    }
    
   
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PagoEntity toEntity() {
        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setId(this.id);
        pagoEntity.setUsuario(this.usuario);
        pagoEntity.setTipo(this.tipo);
        return pagoEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
