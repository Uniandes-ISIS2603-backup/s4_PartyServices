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
import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.TarjetaCreditoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 *
 * @author Jesús Orlando Cárcamo Posada, Elías NegreteS
 */
public class TarjetaCreditoLogic {

    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoLogic.class.getName());

    @Inject
    private TarjetaCreditoPersistence persistence;// Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ClientePersistence persistenceCliente; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public TarjetaCreditoEntity createTarjetaCredito(long clienteId, TarjetaCreditoEntity tarjetaCreditoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear una tarjeta de credito");

        validaciones(tarjetaCreditoEntity);

        ClienteEntity entity = persistenceCliente.find(clienteId);
        tarjetaCreditoEntity.setCliente(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la tarjeta de credito");
        return persistence.create(tarjetaCreditoEntity);

    }

    public TarjetaCreditoEntity getTarjetaCredito(Long clienteid, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{id, clienteid});

        return persistence.find(clienteid, id);
    }

    public TarjetaCreditoEntity updateTarjetaCredito(Long clientesId, TarjetaCreditoEntity tarjetaCredito) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{tarjetaCredito.getId(), clientesId});

        validaciones(tarjetaCredito);
        ClienteEntity entity = persistenceCliente.find(clientesId);
        tarjetaCredito.setCliente(entity);
        persistence.update(tarjetaCredito);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{tarjetaCredito.getId(), clientesId});
        return tarjetaCredito;

    }

    public void deleteTarjetaCredito(Long clientesId, Long tarjetaCreditoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{tarjetaCreditoId, clientesId});
        TarjetaCreditoEntity old = getTarjetaCredito(clientesId, tarjetaCreditoId);
        if (old == null) {
            throw new BusinessLogicException("La tarjeta de credito con id = " + tarjetaCreditoId + " no esta asociada al cliente con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{tarjetaCreditoId, clientesId});
    }

    /**
     * Valida un numero de tarjeta de crédito usando el algoritmo de Luhn,
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
     * Valida un numero de tarjeta de crédito con su empresa
     *
     * @param numero numero de credito
     * @param empresa empresa bancaria asociada a la tarjeta
     * @return true o false si coincide la empresa con el numero
     * @throws
     * co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    public boolean validarFranquiciaCorrecta(Long numero, String empresa) throws BusinessLogicException {
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

    public boolean validaciones(TarjetaCreditoEntity tarjetaCreditoEntity) throws BusinessLogicException {
        //debe tener un numero valido
        if (!validarNumero(tarjetaCreditoEntity.getNumero())) {
            throw new BusinessLogicException("El número de la tarjeta no es valido");
        }

        //la franquicia debe ser coherente con el numero de tarjeta
        validarFranquiciaCorrecta(tarjetaCreditoEntity.getNumero(), tarjetaCreditoEntity.getFranquicia());
            

        //el nombre del titular de la tarjeta debe tener el formato sigguiente:
        String formatoNombre = "^[A-Z\\s]+$";
        Pattern patternNombre = Pattern.compile(formatoNombre);

        Matcher matchNombre = patternNombre.matcher(tarjetaCreditoEntity.getNombreTitular());
        if (!matchNombre.matches()) {
            throw new BusinessLogicException("El nombre del titular de la tarjeta no sigue un formato válido");
        }

        //El codigo de seguridad debe ser de los siguientes digitos
        String formatoCodigoSeguridad = "[0-9]{3,4}";
        Pattern codigoPattern = Pattern.compile(formatoCodigoSeguridad);
        String codigoString = tarjetaCreditoEntity.getCodigoSeguridad().toString();

        Matcher codigoMatcher = codigoPattern.matcher(codigoString);
        if (!codigoMatcher.matches()) {
            throw new BusinessLogicException("El código de seguridad debe estar limitado de 3 a 4 dígitos");
        }

        SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
        Calendar calendario = Calendar.getInstance();
        Date fechaActual = calendario.getTime();

        //la mayoria de las empresas ofrecen una fecha de expiración entre 1 y 5 años. Se asigna 10 para eliminar toda
        //posibilidad
        calendario.add(Calendar.YEAR, 10);
        Date aniosVencimiento = calendario.getTime();
        try {

            Date fechaExpiracionTarjeta = formato.parse(tarjetaCreditoEntity.getFechaExpiracion());
            if (fechaExpiracionTarjeta.compareTo(fechaActual) < 0) {
                throw new BusinessLogicException("La tarjeta de crédito se encuentra vencida");
            }
            if (fechaExpiracionTarjeta.compareTo(aniosVencimiento) > 0) {
                throw new BusinessLogicException("La fecha de expiración futura no es valida");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato: " + ex);
        }

        return true;
    }

}
