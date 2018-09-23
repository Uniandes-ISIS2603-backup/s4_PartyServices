/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un cliente en la persistencia y permite su serialización
 *
 * @author Elias Negrete
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable {

    /*
    *login del cliente
     */
    private String login;

    /*
    *contraseña del cliente
     */
    private String contrasenia;

    /*
    *correo electronico del cliente
     */
    private String email;

    /*
    *fecha de ancimiento del cliente
     */
    private String fechaNacimiento;

    /*
    *relacion uno a mucho hacia eventos
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    List<EventoEntity> eventos;

    /*
    *relacion uno a mucho hacia notificaciones
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    List<NotificacionEntity> notificaciones;

    /*
    *relacion uno a mucho hacia sugerencias
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente",
            fetch = FetchType.LAZY)
    List<SugerenciaEntity> sugerencias;

    /*
    *relacion uno a mucho hacia valoraciones
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente",
            fetch = FetchType.LAZY)
    List<ValoracionEntity> valoraciones;

    /*
    *relacion uno a mucho hacia pagos
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    List<PagoEntity> pagos;

    public ClienteEntity() {

    }

    /**
     * Devuelve la contraseña de un cliente
     *
     * @return the password
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Modifica la contraseña de un cliente
     *
     * @param pContra the password to set
     */
    public void setContrasenia(String pContra) {
        this.contrasenia = pContra;
    }

    /**
     * Devuelve la login de un cliente
     *
     * @return the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Devuelve la email de un cliente
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica la email de un cliente
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Modifica la login de un cliente
     *
     * @param user the user to set
     */
    public void setLogin(String user) {
        this.login = user;
    }

    /**
     * Devuelve los eventos de un cliente
     *
     * @return the events
     */
    public List<EventoEntity> getEventos() {
        return eventos;
    }

    /**
     * Devuelve las sugerencias de un cliente
     *
     * @return the suggestions
     */
    public List<SugerenciaEntity> getSugerencias() {
        return sugerencias;
    }

    /**
     * Devuelve los autores de un libro
     *
     * @return the authors
     */
    public List<PagoEntity> getPagos() {
        return pagos;
    }

    /**
     * Devuelve la notificaciones de un cliente
     *
     * @return the notifications
     */
    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Devuelve la valoracion de un cliente
     *
     * @return the rating
     */
    public List<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    /**
     * Modifica la eventos de un cliente
     *
     * @param pEventos the events to set
     */
    public void setEventos(List<EventoEntity> pEventos) {
        this.eventos = pEventos;
    }

    /**
     * Modifica la pagos de un cliente
     *
     * @param pPagos the paychecks to set
     */
    public void setPagos(List<PagoEntity> pPagos) {
        this.pagos = pPagos;
    }

    /**
     * Modifica la notifiaciones de un cliente
     *
     * @param pNotificaciones the notifications to set
     */
    public void setNotificaciones(List<NotificacionEntity> pNotificaciones) {
        this.notificaciones = pNotificaciones;
    }

    /**
     * Modifica la valoraciones de un cliente
     *
     * @param pValoraciones the ratings to set
     */
    public void setValoraciones(List<ValoracionEntity> pValoraciones) {
        this.valoraciones = pValoraciones;
    }

    /**
     * Modifica la sugerencias de un cliente
     *
     * @param pSugerencias the suggestions to set
     */
    public void setSugerencias(List<SugerenciaEntity> pSugerencias) {
        this.sugerencias = pSugerencias;
    }

    /**
     * Devuelve la fecha de nacimiento de un cliente
     *
     * @return the date
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Modifica la fecja de nacimiento de un cliente
     *
     * @param fechaNacimiento the date to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
