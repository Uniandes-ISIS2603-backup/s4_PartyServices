/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolas Hernandez
 */
public class FechaDetailDTO extends FechaDTO implements Serializable{
    
    /**
     * Eventos de la fecha
     */
    private List<EventoDTO>eventos;
    
    /**
     * Constructor por defecto
     */
    public FechaDetailDTO(){
        super();
        eventos=new ArrayList<>();
    }

    /**
     * Obtiene los eventos de la fecha
     * @return los eventos de la fecha
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * Cambia los eventos de la fecha
     * @param eventos de la fecha
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    
    /**
     * COnstructor en base a una entidad de fecha
     * @param fechaEntity entidad de fecha
     */
    public FechaDetailDTO(FechaEntity fechaEntity){
        super(fechaEntity);
        if(fechaEntity!=null)
        {
            eventos=new ArrayList<>();
            for (EventoEntity evento : fechaEntity.getEventos()) {
                eventos.add(new EventoDTO(evento));
            }
        }
    }
    
    /**
     * Convierte a entidad de fecha
     * @return la entidad de fecha
     */
    @Override
    public FechaEntity toEntity(){
        FechaEntity fechaEntity = super.toEntity();
        if(eventos!=null){
            List<EventoEntity> eventosEntity=new ArrayList<>();
            for (EventoDTO evento : eventos) {
                eventosEntity.add(evento.toEntity());
            }
            fechaEntity.setEventos(eventosEntity);
        }
        return fechaEntity;
        
        
    }
}
