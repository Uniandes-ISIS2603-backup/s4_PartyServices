/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


/**
 * Clase que representa un pago en la persistencia y permite su serializacion
 *
 * @author Elias Negrete
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {
    
  
    
    private String fecha;
    
    private Integer valor;
    
    @PodamExclude
    @OneToOne(
    mappedBy = "pago",
            fetch = FetchType.LAZY
    )
    private EventoEntity evento;

    /*
    *cliente que guarda al pago
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ClienteEntity cliente;

    /**
     * Constructor por defecto
     */
    public PagoEntity() {

        //constructor por defecto
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
    
    /**
     * Método que retorno el cliente
     * @return cliente que contiene este pago
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * Método que edita el cliente al que le pertenece este pago
     * @param cliente 
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

   

}
