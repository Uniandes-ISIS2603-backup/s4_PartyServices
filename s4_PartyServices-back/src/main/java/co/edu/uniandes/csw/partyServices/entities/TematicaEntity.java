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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una tematica en la persistencia y permite su
 * serialización.
 *
 * @author Tomas Vargas
 */

@Entity
public class TematicaEntity extends BaseEntity implements Serializable {
    
    private String name;

    @PodamExclude
    @OneToMany(mappedBy = "tematica",fetch=FetchType.LAZY)
    private List<ServicioEntity> servicios = new ArrayList<ServicioEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "tematica",
            cascade = CascadeType.ALL,
            orphanRemoval = true, 
            fetch=FetchType.LAZY)
    private List<SugerenciaEntity> sugerencias = new ArrayList<SugerenciaEntity>();
    
    
    
    /**
     * Devuelve el nombre de la tematica.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre de la tematica.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Devuelve los servicios de la tematica.
     *
     * @return Lista de entidades de Servicio.
     */

    public List<ServicioEntity> getServicios()
    {
        return servicios;
    }
    
    /**
     * Establece el valor de la colección de servicios.
     *
     * @param servicios nuevo valor de la colección.
     */
    public void setServicios(List<ServicioEntity> servicios) {
        this.servicios = servicios;
    }

    /**
     * Modifica los sugerencias de la tematica.
     *
     * @param sugerencias Los nuevos sugerencias.
     */
    public void setSugerencias(List<SugerenciaEntity> sugerencias) {
        this.sugerencias = sugerencias;
    }

    /**
     * Devuelve los sugerencias de la tematica.
     *
     * @return Lista de entidades de Sugerencia.
     */
    public List<SugerenciaEntity> getSugerencias() 
{

        return sugerencias;
    }

    /**
     * Hash
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final TematicaEntity other = (TematicaEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.servicios, other.servicios)) {
            return false;
        }
        if (!Objects.equals(this.sugerencias, other.sugerencias)) {
            return false;
        }
        return true;
    }

    
    
}

