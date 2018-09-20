/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    private List<ProductoDTO> productos;

    private List<NotificacionDTO> notificaciones;

    public EventoDetailDTO() {
        super();
    }

    public EventoDetailDTO(EventoEntity eventoEntity) {
        super(eventoEntity);
        if (eventoEntity != null) {
            productos = new ArrayList<>();
            for (ProductoEntity entityProductos : eventoEntity.getProductos()) {
                productos.add(new ProductoDTO(entityProductos));
            }
            notificaciones = new ArrayList();
            for (NotificacionEntity entityNotificacion : eventoEntity.getNotificaciones()) {
                notificaciones.add(new NotificacionDTO(entityNotificacion));
            }
        }
    }

    @Override
    public EventoEntity toEntity()
    {
        EventoEntity eventoEntity = super.toEntity();
        if (productos != null) {
            List<ProductoEntity> productosEntity = new ArrayList<>();
            for (ProductoDTO productoDto : productos) {
                productosEntity.add(productoDto.toEntity());
            }
            eventoEntity.setProductos(productosEntity);
        }
        if (notificaciones != null) {
            List<NotificacionEntity> notificionEntity = new ArrayList<>();
            for (NotificacionDTO notificacionDto : notificaciones) {
                notificionEntity.add(notificacionDto.toEntity());
            }
            eventoEntity.setNotificaciones(notificionEntity);
        }
        return eventoEntity;
    }
    
    
    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}
