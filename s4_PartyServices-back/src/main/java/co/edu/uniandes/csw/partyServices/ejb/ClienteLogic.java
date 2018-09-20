/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.util.ConstantesEvento;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class ClienteLogic {
     private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence;

    public ClienteEntity createCliente(ClienteEntity clienteEntity) throws BusinessLogicException {
        //no debe haber dos clientes con el mismo login
        if (persistence.findByLogin(clienteEntity.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el login \"" + clienteEntity.getLogin() + "\"");
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LOGGER.log(Level.INFO, "Se inicia la creación del cliente. A espera de problemas");

        Date fechaAhora = Date.from(Instant.now());
        try {
            Date fechaClienteNacimiento = format.parse(clienteEntity.getFechaNacimiento());
            int añosPermitidos = Period.between(fechaClienteNacimiento.toInstant().atZone(defaultZoneId).toLocalDate(), fechaAhora.toInstant().atZone(defaultZoneId).toLocalDate()).getYears();

            if (fechaClienteNacimiento.compareTo(fechaAhora) > 0) {
                LOGGER.log(Level.INFO, "Hubo un error con la fecha.");

                throw new BusinessLogicException("La fecha de nacimiento es superior a la actual");
            } else if (añosPermitidos < 18) {
                LOGGER.log(Level.INFO, "Hubo un error con la fecha.");

                throw new BusinessLogicException("Un usuario menor de edad no puede organizar una fiesta");
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.INFO, "Hubo un error con la fecha.");

            throw new BusinessLogicException("La fecha de nacimiento no cumple el formato" + ex);
        }

        if (validaciones(clienteEntity)) {
            return persistence.create(clienteEntity);
        } else {
            throw new BusinessLogicException("No se pudo validar una regla de negocio");
        }
    }

   

    /**
     *
     * Obtener todas los clientes existentes en la base de datos.
     *
     * @return una lista de proveedores.
     */
    public List<ClienteEntity> getClientes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ClienteEntity> clientesGuardados = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de todos recoger los clientes");
        return clientesGuardados;
    }
    /**
     *
     * Obtener un cliente por medio de su id.
     *
     * @param clienteID: id del proveedor para ser buscada.
     * @return el cliente solicitado por medio de su id.
     */
    public ClienteEntity getCliente(Long clienteID) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clienteID);
        
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ClienteEntity clienteEntity = persistence.find(clienteID);
        if (clienteEntity == null) {
            LOGGER.log(Level.SEVERE, "el cliente con el id = {0} no existe", clienteID);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", clienteID);
        return clienteEntity;
    }

    /**
     

    /**
     *
     * Actualizar un proveedor.
     *
     * @param clienteEntity: cliente con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el cliente con los cambios actualizados en la base de datos.
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    public ClienteEntity updateCliente( ClienteEntity clienteEntity) throws BusinessLogicException {

        ClienteEntity newEntity = null;

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clienteEntity.getId());

        if (persistence.findByLogin(clienteEntity.getLogin()) == null) {
            throw new BusinessLogicException("No existe un cliente con el login \"" + clienteEntity.getLogin() + "\"");
        }

        if (validaciones(clienteEntity)) {
            newEntity = persistence.update(clienteEntity);
        } else {
            throw new BusinessLogicException("No se pudo validar una regla de negocio");
        }

        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clienteEntity.getId());
        return newEntity;
    }

    public boolean validaciones(ClienteEntity clienteEntity) throws BusinessLogicException {

        if (clienteEntity.getLogin() == null || clienteEntity.getLogin().equals("")) {
            throw new BusinessLogicException("El login no puede ser vacio o nulo, por favor intente nuevamente.");
        }
        
        //validacion sobre la longitud y los cracateres del nombre
        String validacionLogin = "^(?=.{8,20}$)[a-zA-Z0-9]+$";
        Pattern loginPattern = Pattern.compile(validacionLogin);
        Matcher loginMatcher = loginPattern.matcher(clienteEntity.getLogin());

        if (!loginMatcher.matches()) {
            throw new BusinessLogicException("El formato del login no es valido: solo puede contener número o letras en rango de 8 a 20 caracteres");
        }
        
        if(clienteEntity.getLogin().equalsIgnoreCase(clienteEntity.getContrasenia())){
          throw new BusinessLogicException("La contraseña no puede ser igual al login");

        }

        //validacion sobre la longitud y los cracateres de la contraseña
        String validacionContrasenia = "^(?=.{8,8}$)[a-zA-Z0-9]+$";
        Pattern contraseniaPattern = Pattern.compile(validacionContrasenia);
        Matcher contraseniaMatcher = contraseniaPattern.matcher(clienteEntity.getContrasenia());

        if (!contraseniaMatcher.matches()) {
            throw new BusinessLogicException("La contrasenia no sigue el formato: debe ser de máximo 8 caracteres y contener solo números o letras");
        }

        //validacion sobre el formato de un email
        String validacionEmail = "[a-z0-9]+@[a-z]+\\.[a-z]+";
        Pattern emailPattern = Pattern.compile(validacionEmail);
        Matcher emailMatcher = emailPattern.matcher(clienteEntity.getEmail());
        if (!emailMatcher.matches()) {
            throw new BusinessLogicException("El correo no sigue el formato: debe ser de máximo 8 caracteres y contener solo números o letras");
        }

        return true;

    }
    
    
    /**
     * Borrar un cliente
     *
     * @param clienteID: id del cliente a borrar
     * @throws BusinessLogicException Si el cliente a eliminar tiene .
     */
    public void deleteCliente(Long clienteID) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el proveedor con id = {0}", clienteID);
        ClienteEntity clienteEntity = persistence.find(clienteID);
       for (EventoEntity entity : clienteEntity.getEventos()) {
            if(entity.getEstado().equals(ConstantesEvento.EN_PROCESO)){
                throw new BusinessLogicException("No se puede borrar un cliente que tenga un evento en curso");
            }
       }
        
        
        persistence.delete(clienteID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el proveedor con id = {0}", clienteID);
    }

}
