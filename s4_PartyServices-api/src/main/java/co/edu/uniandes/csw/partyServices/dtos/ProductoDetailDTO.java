/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Vargas
 */
public class ProductoDetailDTO extends ProductoDTO implements Serializable{
    
    // relación  cero o muchos evento
    private List<EventoDTO> eventos;

    public ProductoDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param productoEntity La entidad de la cual se construye el DTO
     */
    public ProductoDetailDTO(ProductoEntity productoEntity) {
        super(productoEntity);
        
        if (productoEntity.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEvento : productoEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEvento));
            }
        }
    }
    
     /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el producto.
     */
    @Override
    public ProductoEntity toEntity() {
        ProductoEntity productoEntity = super.toEntity();
        
        if (eventos != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventos) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            productoEntity.setEventos(eventosEntity);
        }
        return productoEntity;
    }

     /**
     * Devuelve los Eventos del producto
     *
     * @return DTO de Autores
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * Modifica los Eventos del producto
     *
     * @param eventos Lista de Eventos
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
}
