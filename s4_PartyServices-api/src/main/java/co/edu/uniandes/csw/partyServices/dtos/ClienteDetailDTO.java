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
    // relación  cero o muchos reviews 
    private List<PagoDTO> pagos;

    // relación  cero o muchos author
    private List<SugerenciaDTO> sugerencias;
    
        private List<NotificacionDTO> notificaciones;

            private List<EventoDTO> eventos;

                private List<ValoracionDTO> valoraciones;

    

                    

    public ClienteDetailDTO() {
        super();
    }

    
    public ClienteDetailDTO(ClienteEntity cliente) {
        super(cliente);
        if (cliente.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityReview : cliente.getEventos()) {
                eventos.add(new EventoDTO(entityReview));
            }
        }
        if (cliente.getSugerencias() != null) {
            sugerencias = new ArrayList<>();
            for (SugerenciaEntity entityReview : cliente.getSugerencias()) {
                sugerencias.add(new SugerenciaDTO(entityReview));
            }
        }
        if (cliente.getPagos()!= null) {
            pagos = new ArrayList<>();
            for (PagoEntity entityReview : cliente.getPagos()) {
                pagos.add(new PagoDTO(entityReview));
            }
        }
        if (cliente.getNotificaciones()!= null) {
            notificaciones = new ArrayList<>();
            for (NotificacionEntity entityReview : cliente.getNotificaciones()) {
                notificaciones.add(new NotificacionDTO(entityReview));
            }
        }if (cliente.getValoraciones()!= null) {
            pagos = new ArrayList<>();
            for (ValoracionEntity valoracionEntity : cliente.getValoraciones()) {
                valoraciones.add(new ValoracionDTO(valoracionEntity));
            }
        }
    }
    
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity bookEntity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> reviewsEntity = new ArrayList<>();
            for (EventoDTO dtoReview : getEventos()) {
                reviewsEntity.add(dtoReview.toEntity());
            }
            bookEntity.setEventos(reviewsEntity);
        }
        if (pagos != null) {
            List<PagoEntity> authorsEntity = new ArrayList<>();
            for (PagoDTO dtoAuthor : getPagos()) {
                authorsEntity.add(dtoAuthor.toEntity());
            }
            bookEntity.setPagos(authorsEntity);
        }
        if (sugerencias != null) {
            List<SugerenciaEntity> authorsEntity = new ArrayList<>();
            for (SugerenciaDTO dtoAuthor : getSugerencias()) {
                authorsEntity.add(dtoAuthor.toEntity());
            }
            bookEntity.setSugerencias(authorsEntity);
        }
        if (notificaciones != null) {
            List<NotificacionEntity> authorsEntity = new ArrayList<>();
            for (NotificacionDTO dtoAuthor : getNotificaciones()) {
                authorsEntity.add(dtoAuthor.toEntity());
            }
            bookEntity.setNotificaciones(authorsEntity);
        }
        if (valoraciones != null) {
            List<ValoracionEntity> authorsEntity = new ArrayList<>();
            for (ValoracionDTO dtoAuthor : getValoraciones()) {
                authorsEntity.add(dtoAuthor.toEntity());
            }
            bookEntity.setValoraciones(authorsEntity);
        }
        return bookEntity;
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
