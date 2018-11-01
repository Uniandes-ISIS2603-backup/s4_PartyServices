/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Gabriel Serna
 */
public class ProveedorDTO implements Serializable{  
  
  private Long id;
  private String nombre ;
  private String contrasenia ;
  private Double calificacion;
  

  
  public ProveedorDTO(){
    
}
  
public ProveedorDTO(ProveedorEntity provEntity) {
        if (provEntity != null) {
            this.id = provEntity.getId();
            this.nombre = provEntity.getNombre();
            this.contrasenia = provEntity.getContrasenia();
            this.calificacion = provEntity.getCalificacion();
            
            
        }
    }

    /**
     * Convierte un objeto AuthorDTO a AuthorEntity.
     *
     * @return Nueva objeto AuthorEntity.
     *
     */
    public ProveedorEntity toEntity() {
        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(this.id);
        proveedorEntity.setNombre(this.getNombre());
        proveedorEntity.setContrasenia(this.getContrasenia());
        proveedorEntity.setCalificacion(this.calificacion);
        
        return proveedorEntity;
    }
    
    public Long getId()
    {
        return id;
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
public void setId(Long pid)
{
    this.id = pid ;
}



  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
