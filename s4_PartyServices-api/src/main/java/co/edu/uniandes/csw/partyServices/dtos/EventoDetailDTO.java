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
 * @author Andres
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    /**
     * Productos de un evento
     */
    private List<ProductoDTO> productos;

    /**
     * Notificaciones de un evento
     */
    private List<NotificacionDTO> notificaciones;

    /**
     * Constructor por defecto
     */
    public EventoDetailDTO() {
        super();
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param eventoEntity Es la entidad que se va a convertir a DTO
     */
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

    /**
     * Convierte el DTO actual a un entity
     *
     * @return un objeto Entity de evento
     */
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
    
    /**
     * Retorna los prdocutos de un evento
     * @return 
     */
    public List<ProductoDTO> getProductos() {
        return productos;
    }

    /**
     * Modifica los productos de un evento
     * @param productos 
     */
    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    
    /**
     * Retorna las notificaciones de un evento
     * @return 
     */
    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Modifica las notificaciones de un evento
     * @param notificaciones 
     */
    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * Metodo ToString
     * @return 
     */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}
