/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Gabriel Serna
 */
public class ProveedorDTO implements Serializable{  
  
  private long id;
  private String nombre ;
  private String contrasenia ;
  private Double calificacion;
  private AgendaDTO agenda;
 

  
  public ProveedorDTO(){
    
}
  
public ProveedorDTO(ProveedorEntity provEntity) {
        if (provEntity != null) {
            this.id = provEntity.getId();
            this.nombre = provEntity.getNombre();
            this.contrasenia = provEntity.getContrasenia();
            this.agenda = provEntity.getAgenda().toEntity();
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
        proveedorEntity.setId(this.getId());
        proveedorEntity.setNombre(this.getNombre());
        proveedorEntity.setContrasenia(this.getContrasenia());
        proveedorEntity.setAgenda(this.agenda);
        return proveedorEntity;
    }
    
    public long getId()
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
public void setID(long pid)
{
    this.id = pid ;
}


  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
