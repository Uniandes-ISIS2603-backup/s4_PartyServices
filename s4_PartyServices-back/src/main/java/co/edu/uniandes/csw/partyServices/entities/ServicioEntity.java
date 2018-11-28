/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un servicio en la persistencia y permite su
 * serialización.
 *
 * @author Tomas Vargas
 */
@Entity
public class ServicioEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
    private List<ProveedorEntity> proveedores = new ArrayList<ProveedorEntity>();

    private String tipo;

    @PodamExclude
    @ManyToOne
    private TematicaEntity tematica;

    /**
     * Devuelve el tipo del servicio.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del servicio.
     *
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ProveedorEntity> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorEntity> proveedores) {
        this.proveedores = proveedores;
    }

    public TematicaEntity getTematica() {
        return tematica;
    }

    public void setTematica(TematicaEntity tematica) {
        this.tematica = tematica;
    }

    public void agregarProveedor(ProveedorEntity proveedor) {
        if (proveedores == null) {
            proveedores = new ArrayList<>();
            proveedores.add(proveedor);
        } else {
            proveedores.add(proveedor);
        }
    }

    /**
     * Hash
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tipo);
        hash = 53 * hash + Objects.hashCode(this.tematica);
        return hash;
    }

    /**
     * Equals
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServicioEntity other = (ServicioEntity) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.proveedores, other.proveedores)) {
            return false;
        }
        if (!Objects.equals(this.tematica, other.tematica)) {
            return false;
        }
        return true;
    }

    
}
