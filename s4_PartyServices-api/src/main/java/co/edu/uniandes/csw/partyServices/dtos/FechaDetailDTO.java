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
 * @author estudiante
 */
public class FechaDetailDTO extends FechaDTO implements Serializable{
    private List<EventoDTO>eventos;
    
    public FechaDetailDTO(){
        super();
        eventos=new ArrayList<>();
    }

    public List<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    
    
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
