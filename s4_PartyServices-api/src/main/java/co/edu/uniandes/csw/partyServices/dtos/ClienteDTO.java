/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PagoDTO Objeto de transferencia de datos de clientes. los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *   "login":STRING,

     "contrasenia":STRING,

     "email":STRING,
  
     "fechaNacimiento":STRING
 *   }
 * </pre> Por ejemplo un pago se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *   "login":"Aquiseinsertaunlogin",

     "contrasenia":"00000000",

     "email":"unemailnotanlargo@cosas.com",
     
     
    
     "fechaNacimiento":"08/12/1999"
 *   }
 *
 * </pre>
 *
 * @author Elias Negrete
 */
public class ClienteDTO implements Serializable {

    /**
     * Id de identificacion
     */
    private Long id;

    /**
     * Login del usuario
     */
    private String login;

    /**
     * Contrasenia para ingresar
     */
    private String contrasenia;

    /**
     * Email de registrp
     */
    private String email;

    /**
     * Fecha de nacimiento
     */
    private String fechaNacimiento;

    /**
     * Constructor por defecto
     */
    public ClienteDTO() {
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param clienteEntity La entidad del cliente
     */
    public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.login = clienteEntity.getLogin();
            this.email = clienteEntity.getEmail();
            this.contrasenia = clienteEntity.getContrasenia();
            this.fechaNacimiento = clienteEntity.getFechaNacimiento();

        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del cliente asociado.
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setLogin(this.login);
        clienteEntity.setEmail(this.email);
        clienteEntity.setContrasenia(this.contrasenia);
        clienteEntity.setFechaNacimiento(this.fechaNacimiento);

        return clienteEntity;
    }

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
     * Devuelve la contrasenia.
     *
     * @return la contrasenia del usuario.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Modifica la conntrasenia del cliente.
     *
     * @param pContra the password to set
     */
    public void setContrasenia(String pContra) {
        this.contrasenia = pContra;
    }

    /**
     * Devuelve el LOGIN del usuario.
     *
     * @return el LOGIN del usuario.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Modifica el LOGIN.
     *
     * @param user nombre de usuario registrado
     */
    public void setLogin(String user) {
        this.login = user;
    }

    /**
     * Devuelve el EMAIL del usuario.
     *
     * @return el email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica el Email.
     *
     * @param email correo electronico de usuario registrado
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la fecha de nacimiento del usuario.
     *
     * @return el email del usuario.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Modifica la fecha de nacimiento.
     *
     * @param fechaNacimiento fecha de nacimiento de usuario registrado
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
