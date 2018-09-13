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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author estudiante
 */
@Entity
public class EventoEntity  extends BaseEntity implements Serializable
{

   
    public enum Estado{
        EN_PLANEACION("En planeacion"),
        PLANEADO ("Planeado"),
        EN_PROCESO ("En proceso"),
        CANCELADO ("Cancelado"),
        TERMINADO ("Terminado"), ;
        
        private final String valor;
        
        private Estado(String valor){
            this.valor=valor;
        }
        
        public static Estado desdeValor(String valor){
            for (Estado estado :  values()) 
            {
                if(estado.darValor().equals(valor)){
                    return estado;
                }
            }
            return null;
        }
        public String darValor(){
            return valor;
        }
        
    }
    
    
    private String nombre ;
    
    private String estado ;
    
    @PodamExclude
    @ManyToOne
    private FechaEntity fecha ;
    
    @PodamExclude
    @ManyToOne()
    private ClienteEntity cliente ;
 
    private double latitud ;
    
    private double longitud ;
    
    @PodamExclude    
    @ManyToMany(
    mappedBy = "eventos" ,
    fetch = FetchType.LAZY
    )
    Collection<ProductoEntity> productos ;
    
    @PodamExclude
    @OneToMany(
    mappedBy = "evento",
    fetch = FetchType.LAZY)
    Collection<NotificacionEntity> notificaciones ;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public FechaEntity getFecha() {
        return fecha;
    }

    public void setFecha(FechaEntity fecha) {
        this.fecha = fecha;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public Collection<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Collection<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    
    
    
    public Collection<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(Collection<ProductoEntity> productos) {
        this.productos = productos;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
   
    
}
