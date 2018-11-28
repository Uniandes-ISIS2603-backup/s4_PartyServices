/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class NotificacionEntity extends BaseEntity implements Serializable {

    private final static long serialVersionUID = 1L;

    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    
    @PodamExclude
    @ManyToOne
    private EventoEntity evento ;
    
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor ;

    private String tipoDeAviso;
    private String mensaje;

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    
    
    
    
    public String getTipoDeAviso() {
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

    /**
     * Hash
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.mensaje);
        return hash;
    }

    /**
     * Equals
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificacionEntity other = (NotificacionEntity) obj;
        if (!Objects.equals(this.tipoDeAviso, other.tipoDeAviso)) {
            return false;
        }
        if (!Objects.equals(this.mensaje, other.mensaje)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.evento, other.evento)) {
            return false;
        }
        if (!Objects.equals(this.proveedor, other.proveedor)) {
            return false;
        }
        return true;
    }

    
    
}
