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
public class FechaDTO implements Serializable{
    private String dia;
    private String jornada;
    public FechaDTO()
    {
        
    }
    public String getDia()
    {
        return dia;
    }
    public String getJornada()
    {
        return jornada;
    }
    public void setDia(String pDia)
    {
        this.dia=pDia;
    }
    public void setJornada(String pJornada)
    {
        this.jornada=pJornada;
    }
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
