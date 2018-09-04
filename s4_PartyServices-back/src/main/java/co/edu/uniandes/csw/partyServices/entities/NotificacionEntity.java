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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
/**
 *
 * @author estudiante
 */
@Entity
public class NotificacionEntity extends BaseEntity implements Serializable{
    private final static long serialVersionUID = 1L ;
    

    private String tipoDeAviso;
    private String mensaje;

    public String getTipoDeAviso(){
        return tipoDeAviso;
    }

    public void setTipoDeAviso(String tipoDeAviso) {
        this.tipoDeAviso = tipoDeAviso;
    }

        public String getMensaje() {
        return mensaje;
    }
        
        public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
        

}
