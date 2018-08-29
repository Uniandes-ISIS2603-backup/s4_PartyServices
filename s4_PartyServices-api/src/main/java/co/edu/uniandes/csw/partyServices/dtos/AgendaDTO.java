/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author n.hernandezs
 */
public class AgendaDTO implements Serializable{
    private String fechasNoDisponibles;
    private String fechaPenitencia;
    
    public AgendaDTO()
    {
        
    }
    public String getFechasNoDisponibles()
    {
        return fechasNoDisponibles;
    }
    public String getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    public void setFechasNoDisponibles(String fechasNoDisponibles)
    {
        this.fechasNoDisponibles=fechasNoDisponibles;
    }
    public void setFechaPenitencia(String fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
