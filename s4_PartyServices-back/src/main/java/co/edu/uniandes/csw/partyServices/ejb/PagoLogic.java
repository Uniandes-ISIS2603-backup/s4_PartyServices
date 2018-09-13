/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class PagoLogic {
    private static final Logger LOGGER = Logger.getLogger(SugerenciaLogic.class.getName());
    
    @Inject
    private PagoPersistence persistence;
    
    
    public PagoEntity createPago(PagoEntity pagoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se inicia la creación del pago");
        
        if(pagoEntity.getTipo().equals(PagoEntity.TIPO_TARJETA_CREDITO)){
            throw new BusinessLogicException("No se aceptan otros medios que no sean tarjeta de crédito por ahora.");
        }
        String datos = pagoEntity.getMedioPago();
        
        if(datos == null || datos.equals("")){
         throw new BusinessLogicException("Los datos de la tarjeta estan vacíos o no son aceptables.");
        }
        String[] arrayDatos = datos.split(",");
        
        int numero = Integer.parseInt(arrayDatos[0]);
        String empresa = arrayDatos[1];


        if(!validarEmpresa(numero, empresa)){
            throw new BusinessLogicException("En numero no coincide con la franquicia");
        }
        persistence.create(pagoEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación de la sugerencia");
        return pagoEntity;
    }
    
    public boolean validarEmpresa(int numero, String empresa){
        int numeroAt = numero;
        while(numeroAt>9){
           numeroAt = numeroAt/10; 
        }
        if(numeroAt == 3 && (empresa.equals("American Express") || empresa.equals("Diners Club"))){
            return true;
        }
        if(numeroAt == 4 && empresa.equals("Visa")){
            return true;
        }
        if(numeroAt == 5 && empresa.equals("MasterCard")){
            return true;
        }
        return false;
    }

    public void deletePago(Long pagoId){
        LOGGER.log(Level.INFO, "se borra el pago", pagoId);
        
        persistence.delete(pagoId);
        
        LOGGER.log(Level.INFO, "Se borró el pago", pagoId);
    }
}

