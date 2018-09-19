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
    private String jornadaLunesND;
    private String jornadaMartesND;
    private String jornadaMiercolesND;
    private String jornadaJuevesND;
    private String jornadaViernesND;
    private String jornadaSabadoND;
    private String jornadaDomingoND;
    
    public AgendaDTO()
    {
        
    }
    
    public AgendaDTO(AgendaEntity agendaEntity){
        if(agendaEntity!=null){
            this.id=agendaEntity.getId();
            this.fechaPenitencia=agendaEntity.getFechaPenitencia();
            this.fechasNoDisponibles=agendaEntity.getFechasNoDisponibles();
            this.proveedorDTO=new ProveedorDTO(agendaEntity.getProveedor());
            this.jornadaLunesND=agendaEntity.getJornadaLunesND();
            this.jornadaMartesND=agendaEntity.getJornadaMartesND();
            this.jornadaMiercolesND=agendaEntity.getJornadaMiercolesND();
            this.jornadaJuevesND=agendaEntity.getJornadaJuevesND();
            this.jornadaViernesND=agendaEntity.getJornadaViernesND();
            this.jornadaSabadoND=agendaEntity.getJornadaSabadoND();
            this.jornadaDomingoND=agendaEntity.getJornadaDomingoND();
        }
    }

    public long getId() 
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJornadaLunesND() {
        return jornadaLunesND;
    }

    public void setJornadaLunesND(String jornadaLunesND) {
        this.jornadaLunesND = jornadaLunesND;
    }

    public String getJornadaMartesND() {
        return jornadaMartesND;
    }

    public void setJornadaMartesND(String jornadaMartesND) {
        this.jornadaMartesND = jornadaMartesND;
    }

    public String getJornadaMiercolesND() {
        return jornadaMiercolesND;
    }

    public void setJornadaMiercolesND(String jornadaMiercolesND) {
        this.jornadaMiercolesND = jornadaMiercolesND;
    }

    public String getJornadaJuevesND() {
        return jornadaJuevesND;
    }

    public void setJornadaJuevesND(String jornadaJuevesND) {
        this.jornadaJuevesND = jornadaJuevesND;
    }

    public String getJornadaViernesND() {
        return jornadaViernesND;
    }

    public void setJornadaViernesND(String jornadaViernesND) {
        this.jornadaViernesND = jornadaViernesND;
    }

    public String getJornadaSabadoND() {
        return jornadaSabadoND;
    }

    public void setJornadaSabadoND(String jornadaSabadoND) {
        this.jornadaSabadoND = jornadaSabadoND;
    }

    public String getJornadaDomingoND() {
        return jornadaDomingoND;
    }

    public void setJornadaDomingoND(String jornadaDomingoND) {
        this.jornadaDomingoND = jornadaDomingoND;
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
        agendaEntity.setJornadaDomingoND(this.jornadaLunesND);
        agendaEntity.setJornadaDomingoND(this.jornadaMartesND);
        agendaEntity.setJornadaDomingoND(this.jornadaMiercolesND);
        agendaEntity.setJornadaDomingoND(this.jornadaJuevesND);
        agendaEntity.setJornadaDomingoND(this.jornadaViernesND);
        agendaEntity.setJornadaDomingoND(this.jornadaSabadoND);
        agendaEntity.setJornadaDomingoND(this.jornadaDomingoND);
        return agendaEntity;
       
    }
}
