/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

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
 * @author Tomas Vargas
 */
public class TematicaDetailDTO extends TematicaDTO implements Serializable {
    
     // relación uno o muchos servicios
    private List<ServicioDTO> servicios;

    // relación  cero o muchos sugerencias 
    private List<SugerenciaDTO> sugerencias;

    public TematicaDetailDTO() {
        super();
    }

    /**
     * Crea un objeto TematicaDetailDTO a partir de un objeto TematicaEntity
     * incluyendo los atributos de TematicaDTO.
     *
     * @param tematicaEntity Entidad TematicaEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public TematicaDetailDTO(TematicaEntity tematicaEntity) {
        super(tematicaEntity);
        if (tematicaEntity != null) {
            servicios = new ArrayList<>();
            for (ServicioEntity entityServicios : tematicaEntity.getServicios()) {
                servicios.add(new ServicioDTO(entityServicios));
            }
            sugerencias = new ArrayList();
            for (SugerenciaEntity entitySugerencia : tematicaEntity.getSugerencias()) {
               // sugerencias.add(new SugerenciaDTO(entitySugerencia));
            }
        }
    }

    /**
     * Convierte un objeto TematicaDetailDTO a TematicaEntity incluyendo los
     * atributos de TematicaDTO.
     *
     * @return Nueva objeto TematicaEntity.
     *
     */
    @Override
    public TematicaEntity toEntity() {
        TematicaEntity tematicaEntity = super.toEntity();
        if (servicios != null) {
            List<ServicioEntity> serviciosEntity = new ArrayList<>();
            for (ServicioDTO dtoServicio : servicios) {
                serviciosEntity.add(dtoServicio.toEntity());
            }
            tematicaEntity.setServicios(serviciosEntity);
        }
        if (sugerencias != null) {
            List<SugerenciaEntity> sugerenciasEntity = new ArrayList<>();
            for (SugerenciaDTO dtoSugerencia : sugerencias) {
                sugerenciasEntity.add(dtoSugerencia.toEntity());
            }
            tematicaEntity.setSugerencias(sugerenciasEntity);
        }
        return tematicaEntity;
    }

    /**
     * Obtiene la lista de libros del autor
     *
     * @return the servicios
     */
    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    /**
     * Modifica la lista de libros para el autor
     *
     * @param servicios the servicios to set
     */
    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }

    /**
     * Obtiene la lista de sugerencias del autor
     *
     * @return the sugerencias
     */
    public List<SugerenciaDTO> getSugerencias() {
        return sugerencias;
    }

    /**
     * Modifica la lista de sugerencias para el autor
     *
     * @param sugerencias the sugerencias to set
     */
    public void setSugerencias(List<SugerenciaDTO> sugerencias) {
        this.sugerencias = sugerencias;
    }

    @Override
    public String toString() {
        
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
