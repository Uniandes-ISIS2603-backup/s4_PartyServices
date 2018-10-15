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
 * @author Nicolas Hernandez
 */
public class AgendaDetailDTO extends AgendaDTO implements Serializable{
    
    /**
     * Las fechas ocupadas de la agenda
     */
    private List<FechaDTO>fechasOcupadas;
    
    /**
     * Constructor por defecto
     */
    public AgendaDetailDTO()
    {
        super();
    }
    
    /**
     * Constructor con base de una entidad de agenda
     * @param agendaEntity la entidad de agenda
     */
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
    
    /**
     * Covertir a entidad. COnvirete a entidad
     * @return la entidad de agenda
     */
    @Override
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

    /**
     * Obtiene las fechas ocupada
     * @return las fechas ocupadas
     */
    public List<FechaDTO> getFechasOcupadas() {
        return fechasOcupadas;
    }

    /**
     * Cambia las fechas ocupadas
     * @param fechasOcupadas las fechas ocupadas
     */
    public void setFechasOcupadas(List<FechaDTO> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }
    
    
    
}
