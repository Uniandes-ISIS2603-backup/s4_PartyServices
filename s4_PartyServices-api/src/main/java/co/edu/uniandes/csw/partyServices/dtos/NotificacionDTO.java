/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class NotificacionDTO {
    private String mensaje;
    private String tipoDeAviso;
    
    public NotificacionDTO()
    {
        
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
      public void setTipoDeAviso(String ptipoDeAviso)
    {
        this.tipoDeAviso = ptipoDeAviso;
    }
      
  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
