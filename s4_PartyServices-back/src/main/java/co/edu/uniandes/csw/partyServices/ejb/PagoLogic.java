/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
***************************************************************************************
*    Title: Version of Luhn's algorithm code
*    Author: Yaritza Miranda
*    Date: 2015
*    Code version: 1.0
*    Availability: https://codepad.co/snippet/4d360a
*
***************************************************************************************
***************************************************************************************
*    Title: Expresiones regulares
*    Author: Janmi
*    Date: 2017
*    Code version: 0
*    Availability: http://janmi.com/como-identificar-el-tipo-de-tarjeta-de-credito-segun-su-numero/
*
***************************************************************************************
 
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Pago.
 *
 * @author Elias NEGRETE
 */
@Stateless
public class PagoLogic {

    private static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());

    @Inject
    private PagoPersistence persistence;// Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ClientePersistence persistenceCliente; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Se encarga de crear un pago en la base de datos si cumple con las reglas
     * de nogico.
     *
     * @param pagoEntity Objeto de PagoEntity con los datos nuevos
     * @param clienteId id del cliente el cual sera padre del nuevo Review.
     * @return Objeto de PagoEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si no cumple con las avrias reglas de
     * negocio
     *
     */
    public PagoEntity createPago(long clienteId, PagoEntity pagoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear pago");

        ClienteEntity entity = persistenceCliente.find(clienteId);

       SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (pagoEntity.getFecha() == null) {
            throw new BusinessLogicException("La fecha no puede ser nula");
        }

        Date fechaAhora = Date.from(Instant.now());
        try {

            Date fecha = format.parse(pagoEntity.getFecha());

            if (fecha.compareTo(fechaAhora) > 0) {
                LOGGER.log(Level.INFO, "Hubo un error con la fecha, no puede ser posterior a la fecha actual.");

                throw new BusinessLogicException("La fecha es superior a la actual");
            } 
        } catch (ParseException ex) {

            throw new BusinessLogicException("La fecha no cumple el formato");
        }
        
        pagoEntity.setCliente(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del pago");

        return persistence.create(pagoEntity);
    }

    /**
     * Obtiene la lista de los registros de pago que pertenecen a un cliente.
     *
     * @param clientesId id del cliente el cual es padre de los pagos.
     * @return Colección de objetos de PagoEntity.
     */
    public List<PagoEntity> getPagos(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los pagos asociados al cliente con id = {0}", clientesId);
        ClienteEntity entity = persistenceCliente.find(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los pagos asociados al book con id = {0}", clientesId);
        return entity.getPagos();
    }

    /**
     * Obtiene los datos de una instancia de pago a partir de su ID. La
     * existencia del elemento padre cliente se debe garantizar.
     *
     * @param clienteid El id del Cliente buscado
     * @param id Identificador de la Pago a consultar
     * @return Instancia de PagoEntity con los datos del Review consultado.
     *
     */
    public PagoEntity getPago(Long clienteid, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el pago con id = {0} del cliente con id = {1}", new Object[]{id, clienteid});

        return persistence.find(clienteid, id);
    }

    /**
     * Actualiza la información de una instancia de pago.
     *
     * @param pagoEntity Instancia de PagoEntity con los nuevos datos.
     * @param clientesId id del cliente el cual sera padre del Cliente
     * actualizado.
     * @return Instancia de PagoEntity con los datos actualizados.
     *
     */
    public PagoEntity updatePago(Long clientesId, PagoEntity pagoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el pago con id = {0} del cliente con id = {1}", new Object[]{pagoEntity.getId(), clientesId});
        ClienteEntity entity = persistenceCliente.find(clientesId);
        pagoEntity.setCliente(entity);
        persistence.update(pagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el pago con id = {0} del cliente con id = {1}", new Object[]{pagoEntity.getId(), clientesId});
        return pagoEntity;
    }

    /**
     * Elimina una instancia de pago de la base de datos.
     *
     * @param pagosId Identificador de la instancia a eliminar.
     * @param clientesId id del cliente el cual es padre del pago.
     * @throws BusinessLogicException Si el pago no esta asociada al cliente
     *
     */
    public void deletePago(Long clientesId, Long pagosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el pago con id = {0} del cliente con id = {1}", new Object[]{pagosId, clientesId});
        PagoEntity old = getPago(clientesId, pagosId);
        if (old == null) {
            throw new BusinessLogicException("El pago con id = " + pagosId + " no esta asociado a el cliente con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el pago con id = {0} del cliente con id = {1}", new Object[]{pagosId, clientesId});
    }
}
