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
public class ServicioDTO implements Serializable
{
    
  private String tematica ;
  private String tipo ;
  private String [] proveedores;


public ServicioDTO(){
    
}

public String getTematica ()
{
    return tematica ;
}
public String getTipo()
{
    return tipo ;
}
public String[] getProveedores()
{
    return proveedores ;
}



public void setTematica(String pTematica)
{
    this.tematica = pTematica;
}
public void setTipo(String pTipo)
{
    this.tipo = pTipo;
}
public void setProveedores(String[] pProveedores)
{
    this.proveedores = pProveedores ;
}




  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }




}
