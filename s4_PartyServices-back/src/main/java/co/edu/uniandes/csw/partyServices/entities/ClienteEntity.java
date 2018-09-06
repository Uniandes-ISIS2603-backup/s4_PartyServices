/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author estudiante
 */
@Entity
public class ClienteEntity  extends BaseEntity implements Serializable
{
   
    private String usuario;
    private String contrasenia;
   
    
    
    @PodamExclude
    @OneToMany(mappedBy="cliente",
            fetch= FetchType.LAZY)
    Collection<EventoEntity> eventos;
    
    
    @PodamExclude
    @OneToMany(mappedBy="cliente",
            fetch= FetchType.LAZY)
    Collection<NotificacionEntity> notificaciones;
    
    @PodamExclude
    @OneToMany(mappedBy="cliente",
            fetch= FetchType.LAZY)
    Collection<SugerenciaEntity> sugerencias;
    
    @PodamExclude
    @OneToMany(mappedBy="cliente",
            fetch= FetchType.LAZY)
    Collection<ValoracionEntity> valoraciones;
    
    @PodamExclude
    @OneToMany(mappedBy="cliente",
            fetch= FetchType.LAZY)
    Collection<PagoEntity> pagos;
    
    public ClienteEntity(){
        
    }
    
   public String getContrasenia()
{
    return contrasenia ;
}
    
    public void setContrasenia(String pContra)
{
    this.contrasenia = pContra;
}
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String user) {
        this.usuario = user;
    }
   
    public Collection<EventoEntity> getEventos() {
        return eventos;
    }
    public Collection<SugerenciaEntity> getSugerencias() {
        return sugerencias;
    }
    public Collection<PagoEntity> getPagos() {
        return pagos;
    }
    public Collection<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }
    public Collection<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    public void setEventos(Collection<EventoEntity> pEventos) {
        this.eventos = pEventos;
    }
    public void setPagos(Collection<PagoEntity> pPagos) {
        this.pagos = pPagos;
    }
    public void setNotificaciones(Collection<NotificacionEntity> pNotificaciones) {
        this.notificaciones = pNotificaciones;
    }
    public void setValoraciones(Collection<ValoracionEntity> pValoraciones) {
        this.valoraciones = pValoraciones;
    }
    public void setSugerencias(Collection<SugerenciaEntity> pSugerencias) {
        this.sugerencias = pSugerencias;
    }
}
