/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ProductoDTO implements Serializable
{
    private Long id ;
    private String nombre;
    private String tipoServicio ;
    private String duenio ;
    private int costo ;
    private int cantidad ;
    private ProveedorDTO proveedor;
    
    public ProductoDTO()
    {
        
    }
    public ProductoDTO(ProductoEntity productoEntity) 
    {
        if (productoEntity != null) 
        {
            this.id = productoEntity.getId();
            this.nombre = productoEntity.getNombre();
            this.tipoServicio = productoEntity.getTipoServicio();
            this.duenio = productoEntity.getDuenio() ;
            this.costo = productoEntity.getCosto();
            this.cantidad = productoEntity.getCantidad() ;
           this.proveedor =  new ProveedorDTO(productoEntity.getProveedor()) ;
  
        }
    }
    
      public ProductoEntity toEntity() 
      {
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setId(this.getId());
        productoEntity.setNombre(this.getNombre());
        productoEntity.setTipoServicio(this.tipoServicio);
        productoEntity.setDuenio(this.duenio);
        productoEntity.setCosto(this.costo);
        productoEntity.setCantidad(this.cantidad);
       productoEntity.setProveedor(this.proveedor.toEntity());
        
        return productoEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getNombre()
    {
        return nombre ;
    }
    public String getTipoServicio()
    {
        return tipoServicio ;
    }
    public String getDuenio()
    {
        return duenio ;
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
    public void setDuenio(String pDueño)
    {
        this.duenio = pDueño;
    }
    public void setCosto(int pCosto)
    {
        this.costo = pCosto ;
    }

    public void setCantidad(int pCantidad)
    {
        this.cantidad = pCantidad ;
    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }
    
   
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}
