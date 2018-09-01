/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
/**
 *
 * @author estudiante
 */
@Entity
public class ProveedorEntity implements Serializable{
    private final static long serialVersionUID = 1L ;
    
    @javax.persistence.Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @OneToOne(
    mappedBy="proveedor"
    )
    AgendaEntity agenda;
    private String nombre;
    private String contrasenia;
    @OneToMany
    Collection<ProductoEntity> catalogoProductos;
    
        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

        public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contasenia) {
        this.contrasenia = contrasenia;
    }
     public Collection<ProductoEntity> getProductos()
    {
        return catalogoProductos;
    }
    public void setProductos(Collection<ProductoEntity> catalogoProductos)
    {
        this.catalogoProductos=catalogoProductos;
    }
}
    
    

      

