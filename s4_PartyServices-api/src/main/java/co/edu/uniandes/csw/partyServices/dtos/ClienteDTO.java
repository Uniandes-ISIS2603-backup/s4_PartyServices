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
 * ClienteDTO Objeto de transferencia de datos de clientes. los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 * {
 * "id":number,
 * "nombreUsuario":string,
 * "contrasenia":string,
 * "email":string,
 * "fechaNacimiento":string
 * }
 * </pre> Por ejemplo un cliente se representa asi:<br>
 *
 * <pre>
 *
 * {
 * "id":1
 * "nombreUsuario":"Aquiseinsertaunlogin",
 * "contrasenia":"00000000",
 * "email":"unemailnotanlargo@cosas.com",
 * "fechaNacimiento":"08/12/1999"
 * }
 *
 * </pre>
 *
 * @author Elias Negrete, Jesús Orlando Cárcamo Posada
 */
public class ClienteDTO implements Serializable {

    /**
     * Id de identificacion
     */
    private Long id;

    /**
     * Nombre del usuario.
     */
    private String nombreUsuario;

    /**
     * Contrasenia para ingresar
     */
    private String contrasenia;

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Fecha de nacimiento del usuario.
     */
    private String fechaNacimiento;

    /*
    * Relación a una tarjeta de credito
    dado que esta tiene cardinalidad 1.
     */
    private TarjetaCreditoDTO tarjetaCredito;

    /**
     * Constructor por defecto
     */
    public ClienteDTO() {
    }

    /**
     * Crea un objeto ClienteDTO a partir de un objeto ClienteEntity.
     *
     * @param clienteEntity La entidad del cliente desde la cual se va a crear
     * el nuevo objeto.
     */
    public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.nombreUsuario = clienteEntity.getNombreUsuario();
            this.contrasenia = clienteEntity.getContrasenia();
            this.email = clienteEntity.getEmail();
            this.fechaNacimiento = clienteEntity.getFechaNacimiento();
            if (clienteEntity.getTarjetaCredito() != null) {
                this.tarjetaCredito = new TarjetaCreditoDTO(clienteEntity.getTarjetaCredito());
            } else {
                clienteEntity.setTarjetaCredito(null);
            }

        }
    }

    /**
     * Convierte un objeto ClienteDTO a ClienteEntity.
     *
     * @return Un nuevo objeto ClienteEntity.
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setNombreUsuario(this.nombreUsuario);
        clienteEntity.setContrasenia(this.contrasenia);
        clienteEntity.setEmail(this.email);
        clienteEntity.setFechaNacimiento(this.fechaNacimiento);
        if (this.tarjetaCredito != null) {
            clienteEntity.setTarjetaCredito(tarjetaCredito.toEntity());
        }
        return clienteEntity;
    }

    /**
     * Devuelve el ID del cliente.
     *
     * @return id. El atributo id del cliente.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del cliente.
     *
     * @param id. El nuevo id que reemplazará al actual.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de usuario del cliente.
     *
     * @return nombreUsuario. El nombre de usuario del cliente.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el nombre de usuario del cliente.
     *
     * @param nombreUsuario. El nuevo nombre de usuario que reemplazará al
     * actual.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Devuelve la contrasenia del cliente.
     *
     * @return contrasenia. La contrasenia del cliente.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Modifica la conntrasenia del cliente.
     *
     * @param contrasenia. La nueva contrasenia que reemplazará a la actual.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Devuelve el email del cliente.
     *
     * @return email. El email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica el email del cliente.
     *
     * @param email. El nuevo email que reemplazará al actual.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la fecha de nacimiento del cliente.
     *
     * @return fechaNacimiento. La fecha de nacimiento del cliente.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Modifica la fecha de nacimiento del cliente.
     *
     * @param fechaNacimiento. La nueva fecha de nacimiento que reemplazará la
     * actual.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
