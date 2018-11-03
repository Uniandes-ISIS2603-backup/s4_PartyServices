/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.FechaEntity;


import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author n.hernandezs
 */
public class FechaDTO implements Serializable{
    
    /**
     * Dia de la fecha
     */
    private Date dia;
    
    /**
     * Jorndada de la fecha
     */
    private String jornada;
    
    /**
     * Id de la fecha
     */
    private long id;
    
    /**
     * Agenda de la fecha
     */
    private AgendaDTO agenda;
    
    /**
     * Constructor por defecto
     */
    public FechaDTO()
    {
        
    }
    
    /**
     * Constructor con base en una entidad de fecha
     * @param fechaEntity la entidad de fecha
     */
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

    /**
     * Obtiene el id de la fecha
     * @return el id de la fecha
     */
    public long getId() 
    {
        return id;
    }

    /**
     * Cambia el id de la fecha
     * @param id el id de la fecha
     */
    public void setId(long id) 
    {
        this.id = id;
    }
    
    /**
     * Obtiene el dia de la fecha
     * @return el dia de la fecha
     */
    public Date getDia()
    {
        return dia;
    }
    
    /**
     * Obtiene la jornada de la fecha
     * @return la jornada de la fecha
     */
    public String getJornada()
    {
        return jornada;
    }
    
    /**
     * Cambia el dia de la fecha
     * @param dia el dia de la fecha
     */
    public void setDia(Date dia)
    {
        this.dia=dia;
    }
    
    /**
     * Cambia la jornada de la fecha
     * @param jornada la jornada de la fecha
     */
    public void setJornada(String jornada)
    {
        this.jornada=jornada;
    }

    /**
     * Obtiene la agenda de la fecha
     * @return la agenda de la fecha
     */
    public AgendaDTO getAgenda() {
        return agenda;
    }

    /**
     * Cambia la agenda de la fecha
     * @param agenda agenda de la fecha
     */
    public void setAgenda(AgendaDTO agenda) {
        this.agenda = agenda;
    }
    
    /**
     * To string
     * @return representacion a String
     */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * Convierte la fecha a entidad
     * @return la entidad de la fecha
     */
    public FechaEntity toEntity()
    {
        FechaEntity fechaEntity = new FechaEntity();
        fechaEntity.setDia(this.dia);
        fechaEntity.setId(this.id);
        fechaEntity.setJornada(this.jornada);
        return fechaEntity;
    }
}
