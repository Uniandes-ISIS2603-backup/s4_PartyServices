/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
//import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author n.hernandezs
 */
public class FechaDTO implements Serializable{
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dia;
    private String jornada;
    private Long id;
    private AgendaDTO agenda;
    public FechaDTO()
    {
        
    }
    public FechaDTO(FechaEntity fechaEntity)
    {
        if(fechaEntity!=null)
        {
            this.id=fechaEntity.getId();
            this.dia=fechaEntity.getDia();
            this.jornada=fechaEntity.getJornada();
            this.agenda=new AgendaDTO(fechaEntity.getAgenda());
     
        }
    }

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDia()
    {
        return dia;
    }
    public String getJornada()
    {
        return jornada;
    }
    public void setDia(Date dia)
    {
        this.dia=dia;
    }
    public void setJornada(String jornada)
    {
        this.jornada=jornada;
    }

    public AgendaDTO getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaDTO agenda) {
        this.agenda = agenda;
    }
    
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    public FechaEntity toEntity()
    {
        FechaEntity fechaEntity = new FechaEntity();
        fechaEntity.setDia(this.dia);
        fechaEntity.setId(this.id);
        fechaEntity.setJornada(this.jornada);
        return fechaEntity;
        
    }
}
