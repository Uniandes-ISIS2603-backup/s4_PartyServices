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
        if (!validarNumero(pagoEntity.getNumeroTarjetaCreditoEntity())) {
            throw new BusinessLogicException("El número de la tarjeta no es valido");
        }
        //la empresa debe ser coherente con el numero de tarjeta
        if (!validarEmpresaCorrecta(pagoEntity.getNumeroTarjetaCreditoEntity(), pagoEntity.getEmpresaEntity())) {
            throw new BusinessLogicException("En número no coincide con la empresa");
        }

        //el nombre en la tarjeta debe tener un formato sgt:
        String formatoNombre = "^[A-Z\\s]+$";
        Pattern patternNombre = Pattern.compile(formatoNombre);

        Matcher matchNombre = patternNombre.matcher(pagoEntity.getNombreTarjetaEntity());
        if (!matchNombre.matches()) {
            throw new BusinessLogicException("El nombre en la tarjeta no sigue un formatoValido");
        }

        //el codigo de seguridad debe ser de los sgts digitos
        String formatoCodigoSeguridad = "[0-9]{3,4}";
        Pattern codigoPattern = Pattern.compile(formatoCodigoSeguridad);
        String codigoString = pagoEntity.getCodigoSeguridadTarjetaEntity().toString();

        Matcher codigoMatcher = codigoPattern.matcher(codigoString);
        if (!codigoMatcher.matches()) {
            throw new BusinessLogicException("El código de seguridad está limitado de 3 a 4 dígitos");
        }

        SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
        Calendar calendario = Calendar.getInstance();
        Date fechaActual = calendario.getTime();

        //la mayoria de las empresas ofrecen una fecha de expiración entre 1 y 5 años. Se asigna 10 para eliminar toda
        //posibilidad
        calendario.add(Calendar.YEAR, 10);
        Date aniosVencimiento = calendario.getTime();
        try {

            Date fechaExpiracionTarjeta = formato.parse(pagoEntity.getFechaExpiracionTarjetaCreditoEntity());
            if (fechaExpiracionTarjeta.compareTo(fechaActual) < 0) {
                throw new BusinessLogicException("La tarjeta de crédito se encuentra vencida");
            }
            if (fechaExpiracionTarjeta.compareTo(aniosVencimiento) > 0) {
                throw new BusinessLogicException("La fecha de expiración futura no es valida");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato: " + ex);
        }
        pagoEntity.setClienteEntity(entity);
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
        pagoEntity.setClienteEntity(entity);
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
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el pago con id = {0} del cliente con id = {1}", new Object[]{pagosId, clientesId});
        PagoEntity old = getPago(clientesId, pagosId);
        if (old == null) {
            throw new BusinessLogicException("El pago con id = " + pagosId + " no esta asociado a el cliente con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el pago con id = {0} del cliente con id = {1}", new Object[]{pagosId, clientesId});
    }

    /**
     * VALIDA un numero de tarjeta de crédito usando el algoritmo de Luhn,
     * acreditado al inicio.
     *
     * @param numero1 el número de tarjeta del cliente
     * @return true o flase si es valido
     */
    public boolean validarNumero(Long numero1) {
        String numero = numero1.toString();
        int s1 = 0;
        int s2 = 0;
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
        return (s1 + s2) % 10 == 0;
    }

    /**
     * VALIDA un numero de tarjeta de crédito con su empresa
     *
     * @param numero numero de credito
     * @param empresa empresa bancaria asociada a la tarjeta
     * @return true o false si coincide la empresa con el numero
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    public boolean validarEmpresaCorrecta(Long numero, String empresa) throws BusinessLogicException {
        //string del numero
        String numeroString = numero.toString();

        String formato = "";
        if (empresa.equalsIgnoreCase("Visa")) {
            formato = "^4[0-9]{6,}$";
        } else if (empresa.equalsIgnoreCase("MasterCard")) {
            formato = "^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$";
        } else if (empresa.equalsIgnoreCase("Diners Club")) {
            formato = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
        } else if (empresa.equalsIgnoreCase("American Express")) {
            formato = "^3[47][0-9]{5,}$";

        } else if (empresa.equalsIgnoreCase("JCB")) {
            formato = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
        } else if (empresa.equalsIgnoreCase("Discover")) {
            formato = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        }
        Pattern codigoPattern = Pattern.compile(formato);

        Matcher codigoMatcher = codigoPattern.matcher(numeroString);
        if (!codigoMatcher.matches()) {
            throw new BusinessLogicException("El numero no cuadra con la empresa. Intente nuevamente");
        }
        return true;
    }

}
