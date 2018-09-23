/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        //debe tener un numero valido
        if (!validarNumero(pagoEntity.getNumeroTarjetaCredito())) {
            throw new BusinessLogicException("El número de la tarjeta no es valido");
        }
        //la empresa debe ser coherente con el numero de tarjeta
        if (!validarNumeroConEmpresa(pagoEntity.getNumeroTarjetaCredito(), pagoEntity.getEmpresa())) {
            throw new BusinessLogicException("En número no coincide con la empresa");
        }

        //el nombre en la tarjeta debe tener un formato sgt:
        String formatoNombre = "^[A-Z\\s]+$";
        Pattern patternNombre = Pattern.compile(formatoNombre);

        Matcher matchNombre = patternNombre.matcher(pagoEntity.getNombreTarjeta());
        if (!matchNombre.matches()) {
            throw new BusinessLogicException("El nombre en la tarjeta no sigue un formatoValido");
        }

        //el codigo de seguridad debe ser de los sgts digitos
        String formatoCodigoSeguridad = "[0-9]{3}";
        Pattern codigoPattern = Pattern.compile(formatoCodigoSeguridad);
        String codigoString = pagoEntity.getCodigoSeguridadTarjeta().toString();

        Matcher codigoMatcher = codigoPattern.matcher(codigoString);
        if (!codigoMatcher.matches()) {
            throw new BusinessLogicException("El código de seguridad está limitado a 3 dígitos");
        }

        SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
        Calendar calendario = Calendar.getInstance();
        Date fechaActual = calendario.getTime();

        //un tarjeta no puede tener un valor de vencimiento de mayor de 18 años
        calendario.add(Calendar.YEAR, 18);
        Date añosVencimiento = calendario.getTime();
        try {

            Date fechaExpiracionTarjeta = formato.parse(pagoEntity.getFechaExpiracionTarjetaCredito());
            if (fechaExpiracionTarjeta.compareTo(fechaActual) < 0) {
                throw new BusinessLogicException("La tarjeta de crédito se encuentra vencida");
            }
            if (fechaExpiracionTarjeta.compareTo(añosVencimiento) > 0) {
                throw new BusinessLogicException("La fecha de expiración futura no es disponible");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato: " + ex);
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
     * @param clienteid  El id del Cliente buscado
     * @param id Identificador de la Pago a consultar
     * @return Instancia de PagoEntity con los datos del Review consultado.
     *
     */
    public PagoEntity getPago(Long clienteid, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el pago con id = {0} del libro con id = " + clienteid, id);

        return persistence.find(clienteid, id);
    }

    /**
     * Actualiza la información de una instancia de pago.
     *
     * @param pagoEntity Instancia de PagoEntity con los nuevos datos.
     * @param clientesId id del cliente el cual sera padre del Cliente actualizado.
     * @return Instancia de PagoEntity con los datos actualizados.
     *
     */
    public PagoEntity updatePago(Long clientesId, PagoEntity pagoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el pago con id = {0} del cliente con id = " + clientesId, pagoEntity.getId());
        ClienteEntity entity = persistenceCliente.find(clientesId);
        pagoEntity.setCliente(entity);
        persistence.update(pagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el pago con id = {0} del cliente con id = " + clientesId, pagoEntity.getId());
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
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el pago con id = {0} del libro con id = " + clientesId, pagosId);
        PagoEntity old = getPago(clientesId, pagosId);
        if (old == null) {
            throw new BusinessLogicException("El pago con id = " + pagosId + " no esta asociado a el cliente con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el pago con id = {0} del cliente con id = " + clientesId, pagosId);
    }

    /**
     * VALIDA un numero de tarjeta de crédito
     *
     * @param numero1 el número de tarjeta del cliente
     * @return true o flase si es valido
     */
    public boolean validarNumero(Long numero1) {
        String numero = numero1.toString();
        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numero).reverse().toString();
        for (int i = 0; i < reversa.length(); i++) {
            int digito = Character.digit(reversa.charAt(i), 10);
            if (i % 2 == 0) {//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            } else {//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if (digito >= 5) {
                    s2 -= 9;
                }
            }
        }
        System.out.println("La tarjeta es:");
        return (s1 + s2) % 10 == 0;
    }

    /**
     * VALIDA un numero de tarjeta de crédito con su empresa
     *
     * @param numero numero de credito
     * @param empresa empresa bancaria asociada a la tarjeta
     * @return true o false si coincide la empresa con el numero
     */
    public boolean validarNumeroConEmpresa(Long numero, String empresa) {
        //get first digit
        String primerDigito = String.valueOf(numero).substring(0, 1);

        if (primerDigito.equals("3") && (empresa.equals("American Express") || empresa.equals("Diners Club"))) {
            return true;
        }
        if (primerDigito.equals("4") && empresa.equals("Visa")) {
            return true;
        } else if (primerDigito.equals("5") && empresa.equals("MasterCard")) {
            return true;
        }
        return false;
    }

}
