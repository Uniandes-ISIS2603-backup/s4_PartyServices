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
 * PagoDTO Objeto de transferencia de datos de clientes.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *  
 *      "usuario": string
 *   }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "usuario": "dark"
 *   }
 *
 * </pre>
 * @author Elias Negrete
 */
public class ClienteDTO implements Serializable{
    
    private Long id;
    private String usuario;

    
    /**
     * Constructor por defecto
     */
    public ClienteDTO() {
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param clienteEntity: Es la entidad que se va a convertir a DTO
     */
    /*public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.tipo = clienteEntity.getUsuario();
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
    
    /**
     * Modifica el ID del cliente.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Devuelve el usuario.
     *
     * @return el  usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Modifica el username.
     *
     * @param user nick
     */
    public void setComentario(String user) {
        this.usuario = user;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    /*public ClienteEntity toEntity() {
        PagoEntity clienteEntity = new ClienteEntity();
        PagoEntity.setId(this.id);
        PagoEntity.setTipo(this.usuario);
        return clienteEntity;
    }*/
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
