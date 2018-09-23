/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author Elias Negrete
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable {

    // relación  cero o muchos pagos 
    private List<PagoDTO> pagos;

    // relación  cero o muchos sugerencias 
    private List<SugerenciaDTO> sugerencias;

    // relación  cero o muchos notificaciones 
    private List<NotificacionDTO> notificaciones;

    // relación  cero o muchos eventos 
    private List<EventoDTO> eventos;

    // relación  cero o muchos valoraciones 
    private List<ValoracionDTO> valoraciones;

    public ClienteDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param cliente La entidad de la cual se construye el DTO
     */
    public ClienteDetailDTO(ClienteEntity cliente) {
        super(cliente);

        if (cliente.getPagos() != null) {
            pagos = new ArrayList<>();
            for (PagoEntity entity : cliente.getPagos()) {
                pagos.add(new PagoDTO(entity));
            }
        }
        if (cliente.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entity : cliente.getEventos()) {
                eventos.add(new EventoDTO(entity));
            }
        }

        if (cliente.getNotificaciones() != null) {
            notificaciones = new ArrayList<>();
            for (NotificacionEntity entity : cliente.getNotificaciones()) {
                notificaciones.add(new NotificacionDTO(entity));
            }
        }

        if (cliente.getSugerencias() != null) {
            sugerencias = new ArrayList<>();
            for (SugerenciaEntity entity : cliente.getSugerencias()) {
                sugerencias.add(new SugerenciaDTO(entity));
            }
        }
        if (cliente.getValoraciones() != null) {
            valoraciones = new ArrayList<>();
            for (ValoracionEntity entity : cliente.getValoraciones()) {
                valoraciones.add(new ValoracionDTO(entity));
            }
        }

    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el cliente.
     */
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity entity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> eventoEntity = new ArrayList<>();
            for (EventoDTO dto : getEventos()) {
                eventoEntity.add(dto.toEntity());
            }
            entity.setEventos(eventoEntity);
        }
        if (pagos != null) {
            List<PagoEntity> pagosEntity = new ArrayList<>();
            for (PagoDTO dto : getPagos()) {
                pagosEntity.add(dto.toEntity());
            }
            entity.setPagos(pagosEntity);
        }
        if (sugerencias != null) {
            List<SugerenciaEntity> sugerenciasEntity = new ArrayList<>();
            for (SugerenciaDTO dto : getSugerencias()) {
                sugerenciasEntity.add(dto.toEntity());
            }
            entity.setSugerencias(sugerenciasEntity);
        }
        if (notificaciones != null) {
            List<NotificacionEntity> notificacionesEntity = new ArrayList<>();
            for (NotificacionDTO dto : getNotificaciones()) {
                notificacionesEntity.add(dto.toEntity());
            }
            entity.setNotificaciones(notificacionesEntity);
        }
        if (valoraciones != null) {
            List<ValoracionEntity> valoracionessEntity = new ArrayList<>();
            for (ValoracionDTO dto : getValoraciones()) {
                valoracionessEntity.add(dto.toEntity());
            }
            entity.setValoraciones(valoracionessEntity);
        }
        return entity;
    }

    /**
     * Devuelve las pagos asociadas a este libro
     *
     * @return Lista de DTOs de pagos
     */
    public List<PagoDTO> getPagos() {
        return pagos;
    }

    /**
     * Modifica las pagos de este libro.
     *
     * @param pagos Las nuevas pagos
     */
    public void setPagos(List<PagoDTO> pagos) {
        this.pagos = pagos;
    }

    /**
     * Devuelve las sugerencias asociadas a este libro
     *
     * @return Lista de DTOs de sugerencias
     */
    public List<SugerenciaDTO> getSugerencias() {
        return sugerencias;
    }

    /**
     * Modifica las sugerencias de este libro.
     *
     * @param sugerencias Las nuevas sugerencias
     */
    public void setSugerencias(List<SugerenciaDTO> sugerencias) {
        this.sugerencias = sugerencias;
    }

    /**
     * Devuelve las notificaciones asociadas a este libro
     *
     * @return Lista de DTOs de notificaciones
     */
    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Modifica las notificaciones de este libro.
     *
     * @param notificaciones Las nuevas notificaciones
     */
    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * Devuelve las eventos asociadas a este libro
     *
     * @return Lista de DTOs de eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * Modifica las eventos de este libro.
     *
     * @param eventos Las nuevas eventos
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    /**
     * Devuelve las valoraciones asociadas a este libro
     *
     * @return Lista de DTOs de valoraciones
     */
    public List<ValoracionDTO> getValoraciones() {
        return valoraciones;
    }

    /**
     * Modifica las valoraciones de este libro.
     *
     * @param valoraciones Las nuevas valoraciones
     */
    public void setValoraciones(List<ValoracionDTO> valoraciones) {
        this.valoraciones = valoraciones;
    }

}
