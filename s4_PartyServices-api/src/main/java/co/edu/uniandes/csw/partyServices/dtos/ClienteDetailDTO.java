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
     * Crea un objeto ClienteDetailDTO a partir de un objeto ClienteEntity
     * incluyendo los atributos de ClienteDTO.
     *
     * @param clienteEntity Entidad ClienteEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) {
        super(clienteEntity);

        if (clienteEntity != null) {

            if (clienteEntity.getPagos() != null) {
                pagos = new ArrayList<>();  
                for (PagoEntity entity : clienteEntity.getPagos()) {
                    pagos.add(new PagoDTO(entity));
                }
            }

            if (clienteEntity.getEventos() != null) {
                eventos = new ArrayList<>();
                for (EventoEntity entity : clienteEntity.getEventos()) {
                    eventos.add(new EventoDTO(entity));
                }
            }

            if (clienteEntity.getNotificaciones() != null) {
                notificaciones = new ArrayList<>();
                for (NotificacionEntity entity : clienteEntity.getNotificaciones()) {
                    notificaciones.add(new NotificacionDTO(entity));
                }
            }

            if (clienteEntity.getSugerencias() != null) {
                sugerencias = new ArrayList<>();
                for (SugerenciaEntity entity : clienteEntity.getSugerencias()) {
                    sugerencias.add(new SugerenciaDTO(entity));
                }
            }
            if (clienteEntity.getValoraciones() != null) {
                valoraciones = new ArrayList<>();
                for (ValoracionEntity entity : clienteEntity.getValoraciones()) {
                    valoraciones.add(new ValoracionDTO(entity));
                }
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
