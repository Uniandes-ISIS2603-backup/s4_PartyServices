/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable {

    private List<PagoDTO> pagos;

    private List<SugerenciaDTO> sugerencias;

    private List<NotificacionDTO> notificaciones;

    private List<EventoDTO> eventos;

    private List<ValoracionDTO> valoraciones;

    public ClienteDetailDTO() {
        super();
    }

    public ClienteDetailDTO(ClienteEntity cliente) {
        super(cliente);

        if (cliente.getPagos() != null) {
            pagos = new ArrayList<>();
            for (PagoEntity entity : cliente.getPagos()) {
                pagos.add(new PagoDTO(entity));
            }
        }
        if (cliente.getEventos()!= null) {
            eventos = new ArrayList<>();
            for (EventoEntity entity : cliente.getEventos()) {
                eventos.add(new EventoDTO(entity));
            }
        }
        
        if (cliente.getNotificaciones()!= null) {
            notificaciones = new ArrayList<>();
            for (NotificacionEntity entity : cliente.getNotificaciones()) {
                notificaciones.add(new NotificacionDTO(entity));
            }
        }
        
        if (cliente.getSugerencias()!= null) {
            sugerencias = new ArrayList<>();
            for (SugerenciaEntity entity : cliente.getSugerencias()) {
                sugerencias.add(new SugerenciaDTO(entity));
            }
        }
        if (cliente.getValoraciones()!= null) {
            valoraciones = new ArrayList<>();
            for (ValoracionEntity entity : cliente.getValoraciones()) {
                valoraciones.add(new ValoracionDTO(entity));
            }
        }

    }

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

    public List<PagoDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoDTO> pagos) {
        this.pagos = pagos;
    }

    public List<SugerenciaDTO> getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(List<SugerenciaDTO> sugerencias) {
        this.sugerencias = sugerencias;
    }

    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    public List<ValoracionDTO> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<ValoracionDTO> valoraciones) {
        this.valoraciones = valoraciones;
    }

}
