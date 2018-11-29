/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ServicioDetailDTO extends ServicioDTO implements Serializable {

    // relación  cero o muchos proveedores
    private List<ProveedorDTO> proveedores;

    public ServicioDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ServicioDetailDTO a partir de un objeto ServicioEntity
     * incluyendo los atributos de ServicioDTO.
     *
     * @param servicioEntity Entidad ServicioEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ServicioDetailDTO(ServicioEntity servicioEntity) {
        super(servicioEntity);
        if (servicioEntity != null) {
            if (servicioEntity.getProveedores() != null) {
                proveedores = new ArrayList<>();  
                for (ProveedorEntity entity : servicioEntity.getProveedores()) {
                    proveedores.add(new ProveedorDTO(entity));
                }
            }
        }
    }

    /**
     * Convierte un objeto ServicioDetailDTO a ServicioEntity incluyendo los
     * atributos de ServicioDTO.
     *
     * @return Nueva objeto ServicioEntity.
     *
     */
    @Override
    public ServicioEntity toEntity() {
        ServicioEntity servicioEntity = super.toEntity();
        if (proveedores != null) {
            List<ProveedorEntity> ProveedoresEntity = new ArrayList<>();
            for (ProveedorDTO dtoProveedor : proveedores) {
                ProveedoresEntity.add(dtoProveedor.toEntity());
            }
            servicioEntity.setProveedores(ProveedoresEntity);
        }
        return servicioEntity;
        }

    /**
     * Obtiene la lista de proveedores del autor
     *
     * @return the proveedores
     */
    public List<ProveedorDTO> getProveedores() {
        return proveedores;
    }

    /**
     * Modifica la lista de proveedores para el autor
     *
     * @param Proveedores the proveedores to set
     */
    public void setProveedores(List<ProveedorDTO> Proveedores) {
        this.proveedores = Proveedores;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}