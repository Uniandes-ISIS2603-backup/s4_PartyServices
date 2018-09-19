/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author n.hernandezs
 */
public class AgendaDTO implements Serializable{
    private long id;
    private String fechasNoDisponibles;
    private Date fechaPenitencia;
    private ProveedorDTO proveedorDTO;
    
    public AgendaDTO()
    {
        
    }
    
    public AgendaDTO(AgendaEntity agendaEntity){
        if(agendaEntity!=null){
            this.id=agendaEntity.getId();
            this.fechaPenitencia=agendaEntity.getFechaPenitencia();
            this.fechasNoDisponibles=agendaEntity.getFechasNoDisponibles();
            this.proveedorDTO=new ProveedorDTO(agendaEntity.getProveedor());
        }
    }

    public long getId() 
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProveedorDTO getProveedorDTO() {
        return proveedorDTO;
    }

    public void setProveedorDTO(ProveedorDTO proveedorDTO) {
        this.proveedorDTO = proveedorDTO;
    }
    
    
    
    public String getFechasNoDisponibles()
    {
        return fechasNoDisponibles;
    }
    public Date getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    public void setFechasNoDisponibles(String fechasNoDisponibles)
    {
        this.fechasNoDisponibles=fechasNoDisponibles;
    }
    public void setFechaPenitencia(Date fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public AgendaEntity toEntity()
    {
        AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setId(this.id);
        agendaEntity.setFechaPenitencia(this.fechaPenitencia);
        agendaEntity.setFechasNoDisponibles(this.fechasNoDisponibles);
        agendaEntity.setProveeedor(this.proveedorDTO.toEntity());
        return agendaEntity;
       
    }
}
