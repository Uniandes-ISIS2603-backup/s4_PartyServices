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
    
     private Long id;
    private String tipo;
    private String usuario;
    private Long numeroTarjetaCredito;
    private Integer codigoSeguridadTarjeta;
    private String fechaExpiracionTarjetaCredito;
    private String nombreTarjeta;
    private String empresa;

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
            this.tipo = pagoEntity.getTipo();
            this.codigoSeguridadTarjeta = pagoEntity.getCodigoSeguridadTarjeta();
            this.empresa = pagoEntity.getEmpresa();
            this.fechaExpiracionTarjetaCredito = pagoEntity.getFechaExpiracionTarjetaCredito();
            this.nombreTarjeta = pagoEntity.getNombreTarjeta();
            this.numeroTarjetaCredito = pagoEntity.getNumeroTarjetaCredito();
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
     * @return el tipo.
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
    
    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }
    
    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }
    
    public Integer getCodigoSeguridadTarjeta() {
        return codigoSeguridadTarjeta;
    }
    
    public void setCodigoSeguridadTarjeta(Integer codigoSeguridadTarjeta) {
        this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
    }
    
    public String getFechaExpiracionTarjetaCredito() {
        return fechaExpiracionTarjetaCredito;
    }
    
    public void setFechaExpiracionTarjetaCredito(String fechaExpiracionTarjetaCredito) {
        this.fechaExpiracionTarjetaCredito = fechaExpiracionTarjetaCredito;
    }
    
    public String getNombreTarjeta() {
        return nombreTarjeta;
    }
    
    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }
    
    public String getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PagoEntity toEntity() {
        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setId(this.id);
        pagoEntity.setTipo(this.tipo);
        pagoEntity.setCodigoSeguridadTarjeta(this.codigoSeguridadTarjeta);
        pagoEntity.setEmpresa(this.empresa);
        pagoEntity.setFechaExpiracionTarjetaCredito(this.fechaExpiracionTarjetaCredito);
        pagoEntity.setNombreTarjeta(this.nombreTarjeta);
        pagoEntity.setNumeroTarjetaCredito(this.numeroTarjetaCredito);
        
        return pagoEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
        
}
