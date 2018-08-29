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
 * @author estudiante
 */
public class ProductoDTO implements Serializable
{
    private String nombre;
    private String tipoServicio ;
    private String dueño ;
    private int costo ;
    private int cantidad ;
    
    public ProductoDTO()
    {
        
    }
    
    public String getNombre()
    {
        return nombre ;
    }
    public String getTipoServicio()
    {
        return tipoServicio ;
    }
    public String getDueño()
    {
        return dueño ;
    }
    public int getCosto()
    {
        return costo ;
    }
    public int getCantidad()
    {
        return cantidad ;
    }
    
    
    public void setNombre(String pNombre)
    {
        this.nombre = pNombre ;
    }
    
    public void setTipoServicio(String pTipoServicio)
    {
        this.tipoServicio = pTipoServicio ;
    }
    public void setDueño(String pDueño)
    {
        this.dueño = pDueño;
    }
    public void setCosto(int pCosto)
    {
        this.costo = pCosto ;
    }

    public void setCantidad(int pCantidad)
    {
        this.cantidad = pCantidad ;
    }
    
   
    
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}
