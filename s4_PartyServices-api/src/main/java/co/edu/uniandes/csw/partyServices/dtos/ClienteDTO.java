/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

//import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
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
 *      "login": "dark"
 *   }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "login": "dark"
 *   }
 *
 * </pre>
 * @author Elias Negrete
 */
public class ClienteDTO implements Serializable{
    
    private Long id;
    private String login;
    private String contrasenia;
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Constructor por defecto
     */
    public ClienteDTO() {
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento).
     *
     * @param clienteEntity: Es la entidad que se va a convertir a DTO
     */
    public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.login = clienteEntity.getLogin();
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
    
    public String getContrasenia()
{
    return contrasenia ;
}
    
    public void setContrasenia(String pContra)
{
    this.contrasenia = pContra;
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
    public String getLogin() {
        return login;
    }

    /**
     * Modifica el username.
     *
     * @param user nick
     */
    public void setLogin(String user) {
        this.login = user;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setLogin(this.login);
        clienteEntity.setCorreo(this.correo);

        return clienteEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
