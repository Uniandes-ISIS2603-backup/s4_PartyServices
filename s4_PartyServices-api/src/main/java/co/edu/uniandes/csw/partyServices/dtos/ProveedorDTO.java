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
 * @author Gabriel Serna
 */
public class ProveedorDTO implements Serializable{  
    
  private String nombre ;
  private String contrasenia ;
  private Double calificacion;
  private String [] catalogoProductos;
  
  public ProveedorDTO(){
    
}
  public String getNombre ()
{
    return nombre ;
}
  public String getContrasenia()
{
    return contrasenia ;
}
public Double getCalificacion()
{
    return calificacion ;
}
public String[] getcatalogoProductos()
{
    return catalogoProductos ;
}

public void setNombre(String pNombre)
{
    this.nombre = pNombre;
}
public void setContrasenia(String pContra)
{
    this.contrasenia = pContra;
}
public void setCalificacion(Double pCalificacion)
{
    this.calificacion = pCalificacion;
}
public void setCatalogoProductos(String[] pCatalogo)
{
    this.catalogoProductos = pCatalogo ;
}


  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
