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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Cliente.
 *
 * @author Elias Negrete
 */
@Stateless
public class ClienteLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence;// Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Guardar un nuevo cliente
     *
     * @param clienteEntity La entidad de tipo cliente del nuevo cliente a
     * persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si no se cumplen las reglas de negocio
     */
    public ClienteEntity createCliente(ClienteEntity clienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se inicia la creación del cliente. A espera de problemas");

        //no debe haber dos clientes con el mismo login
        if (persistence.findByNombreUsuario(clienteEntity.getNombreUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el login \"" + clienteEntity.getNombreUsuario() + "\"");
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ZoneId defaultZoneId = ZoneId.systemDefault();

        if (clienteEntity.getFechaNacimiento() == null) {
            throw new BusinessLogicException("La fecha de nacimiento no puede ser nula");
        }

        Date fechaAhora = Date.from(Instant.now());
        try {

            Date fechaClienteNacimiento = format.parse(clienteEntity.getFechaNacimiento());
            int aniosPermitidos = Period.between(fechaClienteNacimiento.toInstant().atZone(defaultZoneId).toLocalDate(), fechaAhora.toInstant().atZone(defaultZoneId).toLocalDate()).getYears();

            if (fechaClienteNacimiento.compareTo(fechaAhora) > 0) {
                LOGGER.log(Level.INFO, "Hubo un error con la fecha, no puede ser posterior a la fecha actual.");

                throw new BusinessLogicException("La fecha de nacimiento es superior a la actual");
            } else if (aniosPermitidos < 18) {
                LOGGER.log(Level.INFO, "Hubo un error con la edad, no puede ser menor a la requerida.");

                throw new BusinessLogicException("Un usuario menor de edad permitida no puede organizar una fiesta");
            }
        } catch (ParseException ex) {

            throw new BusinessLogicException("La fecha de nacimiento no cumple el formato");
        }

        if (validaciones(clienteEntity)) {
            LOGGER.log(Level.INFO, "Termina proceso de creación del cliente. No hubo problemas");

            return persistence.create(clienteEntity);
        } else {
            throw new BusinessLogicException("No se pudo validar una regla de negocio");
        }
    }

    /**
     *
     * Obtener todas los clientes existentes en la base de datos.
     *
     * @return una lista de clientes.
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
     * Actualizar un cliente.
     *
     * @param clienteId: id de la cliente para buscarla en la base de datos.
     * @param clienteEntity: cliente con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el cliente con los cambios actualizados en la base de datos.
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    public ClienteEntity updateCliente(Long clienteId, ClienteEntity clienteEntity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clienteId);

        if (!validaciones(clienteEntity)) {
            throw new BusinessLogicException("No se pudo validar una regla de negocio");
        }
        ClienteEntity newEntity = persistence.update(clienteEntity);

        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clienteId);
        return newEntity;
    }

    /**
     * Valida las diferentes reglas de negocio
     *
     * @param clienteEntity La entidad de tipo cliente del nuevo cliente a
     * persistir.
     * @return true dependiendo de si se valida todas las reglas o una excpeción
     * en caso de que no
     * @throws BusinessLogicException Si no se cumplen las reglas de negocio
     */
    public boolean validaciones(ClienteEntity clienteEntity) throws BusinessLogicException {

        //el login no puede ser vacio o nulo
        if (clienteEntity.getNombreUsuario() == null || clienteEntity.getNombreUsuario().equals("")) {
            throw new BusinessLogicException("El login no puede ser vacio o nulo, por favor intente nuevamente.");
        }

        //validacion sobre la longitud y los cracateres del nombre
        String validacionLogin = "^(?=.{8,20}$)[a-zA-Z0-9]+$";
        Pattern loginPattern = Pattern.compile(validacionLogin);
        Matcher loginMatcher = loginPattern.matcher(clienteEntity.getNombreUsuario());

        if (!loginMatcher.matches()) {
            throw new BusinessLogicException("El formato del login no es valido: solo puede contener número o letras en rango de 8 a 20 caracteres");
        }

        //el login no puede ser igual a la contraseña
        if (clienteEntity.getNombreUsuario().equalsIgnoreCase(clienteEntity.getContrasenia())) {
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
     * @throws BusinessLogicException Si el cliente a eliminar tiene eventos en
     * progreso.
     */
    public void deleteCliente(Long clienteID) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clienteID);
        List<EventoEntity> eventos = getCliente(clienteID).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            for (EventoEntity entity : eventos) {
                if (entity.getEstado().equals(ConstantesEvento.EN_PROCESO)) {
                    throw new BusinessLogicException("No se puede borrar un cliente que tenga un evento en curso");
                }

            }

        }

        persistence.delete(clienteID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", clienteID);
    }

}
