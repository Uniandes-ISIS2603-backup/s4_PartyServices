/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author n.hernandezs
 */
public class FechaDTO implements Serializable{
    private Date dia;
    private String jornada;
    public FechaDTO()
    {
        
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
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
