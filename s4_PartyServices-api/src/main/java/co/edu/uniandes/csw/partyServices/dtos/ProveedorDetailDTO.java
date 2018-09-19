/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ProveedorDetailDTO extends ProveedorDTO implements Serializable {

    private List<NotificacionDTO> notificaciones;
    private List<ValoracionDTO> valoraciones;
    private List<ProductoDTO> productos;

    public ProveedorDetailDTO() {
        super();
    }

    /**
     * Crea un objeto AuthorDetailDTO a partir de un objeto AuthorEntity
     * incluyendo los atributos de AuthorDTO.
     *
     * @param authorEntity Entidad AuthorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ProveedorDetailDTO(ProveedorEntity proveedorEntity) {
        super(proveedorEntity);
        if (proveedorEntity != null) {
            productos = new ArrayList<>();
            for (ProductoEntity entityProducto : proveedorEntity.getCatalogoProductos()) {
                productos.add(new ProductoDTO(entityProducto));
            }
            notificaciones = new ArrayList();
            for (NotificacionEntity entityNotificacion : proveedorEntity.getNotificaciones()) {
                notificaciones.add(new NotificacionDTO(entityNotificacion));
            }
            valoraciones = new ArrayList();
            for (ValoracionEntity entityValoracion : proveedorEntity.getValoraciones()) {
                valoraciones.add(new ValoracionDTO(entityValoracion));
            }
        }
    }

    /**
     * Convierte un objeto AuthorDetailDTO a AuthorEntity incluyendo los
     * atributos de AuthorDTO.
     *
     * @return Nueva objeto AuthorEntity.
     *
     */
    @Override
    public ProveedorEntity toEntity() {
        ProveedorEntity proveedorEntity = super.toEntity();
        if (productos != null) {
            List<ProductoEntity> productoEntity = new ArrayList<>();
            for (ProductoDTO dtoProducto : productos) {
                productoEntity.add(dtoProducto.toEntity());
            }
            proveedorEntity.setCatalogoProductos(productosEntity);
        }
        if (prizes != null) {
            List<PrizeEntity> prizesEntity = new ArrayList<>();
            for (PrizeDTO dtoPrize : prizes) {
                prizesEntity.add(dtoPrize.toEntity());
            }
            authorEntity.setPrizes(prizesEntity);
        }
        return authorEntity;
    }

    /**
     * Obtiene la lista de libros del autor
     *
     * @return the books
     */
    public List<ProductoDTO> getCatalogoProductos() {
        return productos;
    }


    public void setCatalogoProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }


    public List<NotificacionDTO> getNotificacion() {
        return notificaciones;
    }

    public void setNotificacion(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<ValoracionDTO> getValoracion(){
        return valoraciones;
    }
    
    public void setValoracion(List<ValoracionDTO> valoraciones){
       this.valoraciones = valoraciones;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}