/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class PagoLogic {
    
    
    
    private static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());

    @Inject
    private PagoPersistence persistence;

    public PagoEntity createPago(PagoEntity PagoEntity) throws BusinessLogicException {

        if (!validarNumero(PagoEntity.getNumeroTarjetaCredito())) {
            throw new BusinessLogicException("El número de la tarjeta no es valido");
        }
        if (!validarNumeroConEmpresa(PagoEntity.getNumeroTarjetaCredito(), PagoEntity.getEmpresa())) {
            throw new BusinessLogicException("En número no coincide con la empresa");
        }
        
        String formatoNombre = "^[A-Z\\s]+$";
        Pattern patternNombre = Pattern.compile(formatoNombre);
        
        Matcher matchNombre = patternNombre.matcher(PagoEntity.getNombreTarjeta());
        if (!matchNombre.matches()) {
            throw new BusinessLogicException("El nombre en la tarjeta no sigue un formatoValido");
        }
        

        String formatoCodigoSeguridad = "[0-9]{3}";
        Pattern codigoPattern = Pattern.compile(formatoCodigoSeguridad);
        String codigoString = PagoEntity.getCodigoSeguridadTarjeta().toString();
        
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

            Date fechaExpiracionTarjeta = formato.parse(PagoEntity.getFechaExpiracionTarjetaCredito());
            if (fechaExpiracionTarjeta.compareTo(fechaActual) < 0){
                throw new BusinessLogicException("La tarjeta de crédito se encuentra vencida");
            }
            if (fechaExpiracionTarjeta.compareTo(añosVencimiento) > 0) {
                throw new BusinessLogicException("La fecha de expiración futura no es disponible");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato: "+ ex);
        }

       return persistence.create(PagoEntity);
    }

    public PagoEntity getPago(Long id) {
        return persistence.find(id);
    }
    
    public PagoEntity updatePago(PagoEntity pago) {
        return persistence.update(pago);
    }

    public void deletePago(Long id) {
        persistence.delete(id);
    }

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

    public boolean validarNumeroConEmpresa(Long numero, String empresa) {
        //get first digit
        String primerDigito = String.valueOf(numero).substring(0, 1);

        if (primerDigito.equals("3") && (empresa.equals("American Express") || empresa.equals("Diners Club"))) {
            return true;
        }
        if (primerDigito.equals("4") && empresa.equals("Visa")) {
            return true;
        }
        else if (primerDigito.equals("5") && empresa.equals("MasterCard")) {
            return true;
        }
        return false;
    }
    
}
