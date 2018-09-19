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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Elias Negrete
 */
@Stateless
public class PagoLogic {
    private  static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());
    
    
    @Inject
    private PagoPersistence persistence;
    
    public PagoEntity createPago(PagoEntity PagoEntity)throws BusinessLogicException, ParseException{
        
        if(!validarNumero(PagoEntity.getNumeroTarjetaCredito())){
            throw new BusinessLogicException("El numero de la tarjeta no es valido");
        }
        if(!validarNumeroConEmpresa(PagoEntity.getNumeroTarjetaCredito(), PagoEntity.getEmpresa())){
            throw new BusinessLogicException("En numero no coincide con la empresa");
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/yy");
        Calendar nowCal = Calendar.getInstance();
        Date now = nowCal.getTime();
        nowCal.add(Calendar.YEAR, 20);
        Date veinteYrs = nowCal.getTime(); 
        try {
    Date fechaExpiracionEntidad = format.parse(PagoEntity.getFechaExpiracionTarjetaCredito());
            if(fechaExpiracionEntidad.compareTo(now) < 0){
                throw new BusinessLogicException("La tarjeta de credito ya esta vencida");
            }
            if(fechaExpiracionEntidad.compareTo(veinteYrs) > 0){
                throw new BusinessLogicException("la fecha de expiracion es 20 anos superior a la actual, lo que la hace no valida");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato");
        }
        String ccvValidationPattern = "[0-9]{3}";
        Pattern patternCcv = Pattern.compile(ccvValidationPattern);
        String ccvEnString = PagoEntity.getCodigoSeguridadTarjeta().toString();
        Matcher matchCcv = patternCcv.matcher(ccvEnString);
        if(!matchCcv.matches()){
            throw new BusinessLogicException("El codigo de seguridad no sigue el formato, solo puede ser tres digitos numericos");
        }
        String nombreValidartionPattern = "^[a-zA-Z\\s]+$";
        Pattern patternNombre = Pattern.compile(nombreValidartionPattern);
        Matcher matchNombre = patternNombre.matcher(PagoEntity.getNombreTarjeta());
        if(!matchNombre.matches()){
            throw new BusinessLogicException("El nombre en la tarjeta no sigue un formatoValido");
        }
        return persistence.create(PagoEntity);
    }   
    
    public PagoEntity getPago(Long id){
        return persistence.find(id);
    }
    public void deletePago(Long id){
        persistence.delete(id);
    }

    public boolean validarNumero(Long numero1){
        String numero = numero1.toString();
        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numero).reverse().toString();
        for(int i = 0 ;i < reversa.length();i++){
            int digito = Character.digit(reversa.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if(digito >= 5){
                    s2 -= 9;
                }
            }
        }
        System.out.println("La tarjeta es:");
        return (s1 + s2) % 10 == 0;
    }
    public boolean validarNumeroConEmpresa(Long numero, String empresa){
//get first digit
        String firstDigit = String.valueOf(numero).substring(0,1);

        if(firstDigit.equals("3") && (empresa.equals("American Express") || empresa.equals("Diners Club"))){
            return true;
        }
        if(firstDigit.equals("4") && empresa.equals("Visa")){
            return true;
        }
        else if( firstDigit.equals("5")&&empresa.equals("MasterCard") ){
            return true;
        }
        return false;
    }
    
    
    

}
