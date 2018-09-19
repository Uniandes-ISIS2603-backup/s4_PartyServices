/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
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
    private List<ProveedorDTO> Proveedores;

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
            Proveedores = new ArrayList<>();
            for (ProveedorEntity entityProveedores : servicioEntity.getProveedores()) {
               // -------------------------------------------- Proveedores.add(new ProveedorDTO(entityProveedores));
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
        if (Proveedores != null) {
            List<ProveedorEntity> ProveedoresEntity = new ArrayList<>();
            for (ProveedorDTO dtoProveedor : Proveedores) {
          // ----------------------------------------------------      ProveedoresEntity.add(dtoProveedor.toEntity());
            }
            servicioEntity.setProveedores(ProveedoresEntity);
        }
        return servicioEntity;
        }

    /**
     * Obtiene la lista de proveedores del autor
     *
     * @return the Proveedores
     */
    public List<ProveedorDTO> getProveedores() {
        return Proveedores;
    }

    /**
     * Modifica la lista de proveedores para el autor
     *
     * @param Proveedores the Proveedores to set
     */
    public void setProveedores(List<ProveedorDTO> Proveedores) {
        this.Proveedores = Proveedores;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}