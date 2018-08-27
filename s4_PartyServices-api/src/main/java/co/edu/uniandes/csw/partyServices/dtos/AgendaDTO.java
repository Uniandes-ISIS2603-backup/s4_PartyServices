/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

/**
 *
 * @author n.hernandezs
 */
public class AgendaDTO {
    private String diasNoHabiles;
    private String fechaPenitencia;
    
    public AgendaDTO()
    {
        
    }
    public String getDiasNoHabiles()
    {
        return diasNoHabiles;
    }
    public String getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    public void setDiasNoHabiles(String pDiasNoHabliles)
    {
        this.diasNoHabiles=pDiasNoHabliles;
    }
    public void setFechaPenitencia(String pFechaPenitencia)
    {
        this.fechaPenitencia=pFechaPenitencia;
    }
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
