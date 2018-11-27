/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TarjetaCreditoDTO Objeto de transferencia de datos de las tarjetas de
 * credito. los DTO contienen las representaciones de los JSON que se
 * transfieren entre el cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 * {
 * "id":number,
 * "nombreTitular":string,
 * "numero":number,
 * "codigoSeguridad":number,
 * "fechaExpiracion":String
 * "banco":string,
 * "franquicia":string
 * }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 * {
 * "id":1
 * "nombreTitular":"Jesus Orlando Carcamo Posada",
 * "numero":"1234567890123456",
 * "codigoSeguridad":"1234",
 * "fechaExpiracion":"12/20",
 * "banco":"Bancolombia",
 * "franquicia":"MasterCard"
 * }
 *
 * </pre>
 *
 * @author Jesús Orlando Cárcamo Posada
 */
public class TarjetaCreditoDTO implements Serializable {

    /*
    * id de la tarjeta de Credito.
     */
    private Long id;

    /*
    * Nombre del titular de la tarjeta.
     */
    private String nombreTitular;

    /*
    * Número de la tarjeta de credito 
     */
    private Long numero;

    /*
    * Código de seguridad de la tarjeta.
     */
    private Integer codigoSeguridad;

    /*
    * fecha de expiracion de la tarjeta 
     */
    private String fechaExpiracion;

    /*
    * Nombre del banco de la tarjeta.
     */
    private String banco;

    /*
    * Nombre de la franquicia de la tarjeta.
     */
    private String franquicia;

    /**
     * Constructor por defecto
     */
    public TarjetaCreditoDTO() {
    }

    /**
     * Crea un objeto TarjetaCreditoDTO a partir de un objeto
     * TarjetaCreditoEntity.
     *
     * @param tarjetaCreditoEntity La entidad de la tarjeta de credito desde la
     * cual se va a crear el nuevo objeto.
     */
    public TarjetaCreditoDTO(TarjetaCreditoEntity tarjetaCreditoEntity) {
        if (tarjetaCreditoEntity != null) {
            this.id = tarjetaCreditoEntity.getId();
            this.nombreTitular = tarjetaCreditoEntity.getNombreTitular();
            this.numero = tarjetaCreditoEntity.getNumero();
            this.codigoSeguridad = tarjetaCreditoEntity.getCodigoSeguridad();
            this.fechaExpiracion = tarjetaCreditoEntity.getFechaExpiracion();
            this.banco = tarjetaCreditoEntity.getBanco();
            this.franquicia = tarjetaCreditoEntity.getFranquicia();

        }
    }

    /**
     * Convierte un objeto TarjetaCreditoDTO a TarjetaCreditoEntity.
     *
     * @return Un nuevo objeto TarjetaCreditoEntity.
     */
    public TarjetaCreditoEntity toEntity() {
        TarjetaCreditoEntity tarjetaCreditoEntity = new TarjetaCreditoEntity();
        tarjetaCreditoEntity.setId(this.id);
        tarjetaCreditoEntity.setNombreTitular(this.nombreTitular);
        tarjetaCreditoEntity.setNumero(this.numero);
        tarjetaCreditoEntity.setCodigoSeguridad(this.codigoSeguridad);
        tarjetaCreditoEntity.setFechaExpiracion(this.fechaExpiracion);
        tarjetaCreditoEntity.setBanco(this.banco);
        tarjetaCreditoEntity.setFranquicia(this.franquicia);
        return tarjetaCreditoEntity;
    }

    /**
     * Devuelve el ID de la tarjeta de credito.
     *
     * @return id. El atributo id de la tarjeta de credito.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica ID de la tarjeta de credito.
     *
     * @param id. El nuevo id que reemplazará al actual.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nommbre del titular de la tarjeta de credito.
     *
     * @return nombreTitular. El nombre del titular de la tarjeta de credito.
     */
    public String getNombreTitular() {
        return nombreTitular;
    }

    /**
     * Modifica el nombre del titular de la tarjeta de credito.
     *
     * @param nombreTitular. El nuevo nombre del titular que reemplazará al actual.
     */
    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
