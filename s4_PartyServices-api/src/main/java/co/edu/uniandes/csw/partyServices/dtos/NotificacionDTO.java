/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class NotificacionDTO {
    private long id;
    private String mensaje;
    private String tipoDeAviso;
    
    
    public NotificacionDTO(NotificacionEntity notificacionEntity) {
        if (notificacionEntity != null) {
            this.mensaje = notificacionEntity.getMensaje();
            this.tipoDeAviso = notificacionEntity.getTipoDeAviso();
        }
    }
    public NotificacionDTO()
    {
        
    }
    
     public NotificacionEntity toEntity() {
        NotificacionEntity notifEntity = new NotificacionEntity();
        notifEntity.setMensaje(this.getMensaje());
        notifEntity.setTipoDeAviso(this.getTipoDeAviso());
        return notifEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
     
    public String getMensaje()
    {
        return mensaje;
    }
    public String getTipoDeAviso()
    {
        return tipoDeAviso;
    }
    
    public void setMensaje(String pMensaje)
    {
        this.mensaje = pMensaje;
    }
    
    public void setID (Long pId)
    {
        this.id = pId;
    }
      public void setTipoDeAviso(String ptipoDeAviso)
    {
        this.tipoDeAviso = ptipoDeAviso;
    }
      
  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
