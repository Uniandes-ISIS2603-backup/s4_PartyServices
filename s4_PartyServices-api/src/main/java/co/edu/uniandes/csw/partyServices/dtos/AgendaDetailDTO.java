/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class AgendaDetailDTO extends AgendaDTO implements Serializable{
    
    private List<FechaDTO>fechasOcupadas;
    
    public AgendaDetailDTO()
    {
        super();
    }
    
    public AgendaDetailDTO(AgendaEntity agendaEntity)
    {
        super(agendaEntity);
        if(agendaEntity!=null){
            fechasOcupadas=new ArrayList<>();
            for (FechaEntity fecha : agendaEntity.getFechasOcupadas()) {
                fechasOcupadas.add(new FechaDTO(fecha));
            }
        }
    }
    
    public AgendaEntity toEntity()
    {
        AgendaEntity agendaEntity = super.toEntity();
        if(fechasOcupadas!=null){
            List<FechaEntity> fechasEntity =new ArrayList<>();
            for (FechaDTO fecha : fechasOcupadas) {
                fechasEntity.add(fecha.toEntity());
            }
            agendaEntity.setFechasOcupadas(fechasEntity);
        }
        return agendaEntity;
    }

    public List<FechaDTO> getFechasOcupadas() {
        return fechasOcupadas;
    }

    public void setFechasOcupadas(List<FechaDTO> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }
    
    
    
}
