/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.util;

/**
 *
 * @author estudiante
 */
public enum ConstantesJornada{
        JORNADA_MANANA("Manana"),
        JORNADA_TARDE("Tarde"),
        JORNADA_NOCHE("Noche"),
        JORNADA_COMPLETA("Completa"),
        JORNADA_MANANA_TARDE("Manana_tarde"),
        JORNADA_TARDE_NOCHE("Tarde_noche"),
        JORNADA_MANANA_NOCHE("Manana_noche"),
        NINGUNA("Ninguna");
        
        
        private final String valor;
        
       
        
        private ConstantesJornada(String valor){
            this.valor=valor;
        }
        
        public static ConstantesJornada desdeValor(String valor){
            for (ConstantesJornada jornada :  values()) {
                if(jornada.darValor().equals(valor)){
                    return jornada;
                }
            }
            return null;
        }
        
        public String darValor(){
            return valor;
        }
        
    }
