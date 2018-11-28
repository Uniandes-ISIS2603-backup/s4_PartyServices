/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.entities;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Entidad de agenda
 * @author Nicolas Hernandez
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable{
   
    
    /**
     * Fecha de penitencia de la agenda
     */
    @Temporal(TemporalType.DATE)
    private Date fechaPenitencia;

    /**
     * Jornada que no labora el proveedor los lunes
     */
    private String jornadaLunesND;
    
    /**
     * Jornada que no labora el proveedor los martes
     */
    private String jornadaMartesND;
    
    /**
     * Jornada que no labora el proveedor los miercoles
     */
    private String jornadaMiercolesND;
    
    /**
     * Jornada que no labora el proveedor los jueves
     */
    private String jornadaJuevesND;
    
    /**
     * Jornada que no labora el proveedor los viernes
     */
    private String jornadaViernesND;
    
    /**
     * Jornada que no labora el proveedor los sabado
     */
    private String jornadaSabadoND;
    
    /**
     * Jornada que no labora el proveedor los domingo
     */
    private String jornadaDomingoND;
    
    /**
     * Relacion uno a muchos con las fechas ocupadas de la agenda
     */
    @PodamExclude
    @OneToMany(
            mappedBy="agenda",
            cascade = CascadeType.ALL, 
            orphanRemoval =true ,
            //Terminan en many son lazy. Eger terminan en One
            fetch= FetchType.LAZY
    )
    private Collection<FechaEntity> fechasOcupadas;
    
    /**
     * Relacion uno a uno con el proveedor de la agenda
     */
    @PodamExclude
    @OneToOne( )
    private ProveedorEntity proveedor;

    /**
     * Obtiene las jornadas que no labora los lunes
     * @return jornada que no labora los lunes
     */
    public String getJornadaLunesND() {
        return jornadaLunesND;
    }

    /**
     * Cambia la jornada que no labora los lunes
     * @param jornadaLunesND nueva jornada
     */
    public void setJornadaLunesND(String jornadaLunesND) {
        this.jornadaLunesND = jornadaLunesND;
    }

    /**
     * Obtiene las jornadas que no labora los martes
     * @return jornada que no labora los martes
     */
    public String getJornadaMartesND() {
        return jornadaMartesND;
    }

    /**
     * Cambia la jornada que no labora los martes
     * @param jornadaMartesND nueva jornada
     */
    public void setJornadaMartesND(String jornadaMartesND) {
        this.jornadaMartesND = jornadaMartesND;
    }

    /**
     * Obtiene las jornadas que no labora los miercoles
     * @return jornada que no labora los miercoles
     */
    public String getJornadaMiercolesND() {
        return jornadaMiercolesND;
    }

    /**
     * Cambia la jornada que no labora los miercoles
     * @param jornadaMiercolesND nueva jornada
     */
    public void setJornadaMiercolesND(String jornadaMiercolesND) {
        this.jornadaMiercolesND = jornadaMiercolesND;
    }

    /**
     * Obtiene las jornadas que no labora los jueves
     * @return jornada que no labora los jueves
     */
    public String getJornadaJuevesND() {
        return jornadaJuevesND;
    }

    /**
     * Cambia la jornada que no labora los jueves
     * @param jornadaJuevesND nueva jornada
     */
    public void setJornadaJuevesND(String jornadaJuevesND) {
        this.jornadaJuevesND = jornadaJuevesND;
    }

    /**
     * Obtiene las jornadas que no labora los viernes
     * @return jornada que no labora los viernes
     */
    public String getJornadaViernesND() {
        return jornadaViernesND;
    }

    /**
     * Cambia la jornada que no labora los viernes
     * @param jornadaViernesND nueva jornada
     */
    public void setJornadaViernesND(String jornadaViernesND) {
        this.jornadaViernesND = jornadaViernesND;
    }

    /**
     * Obtiene las jornadas que no labora los sabados
     * @return jornada que no labora los sabados
     */
    public String getJornadaSabadoND() {
        return jornadaSabadoND;
    }

    /**
     * Cambia la jornada que no labora los sabados
     * @param jornadaSabadoND nueva jornada
     */
    public void setJornadaSabadoND(String jornadaSabadoND) {
        this.jornadaSabadoND = jornadaSabadoND;
    }

    /**
     * Obtiene las jornadas que no labora los domingos
     * @return jornada que no labora los domingos
     */
    public String getJornadaDomingoND() {
        return jornadaDomingoND;
    }

    /**
     * Cambia la jornada que no labora los domingos
     * @param jornadaDomingoND nueva jornada
     */
    public void setJornadaDomingoND(String jornadaDomingoND) {
        this.jornadaDomingoND = jornadaDomingoND;
    }

    /**
     * Cambia el proveedor de la agenda
     * @param proveedor proveedor de la agenda
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }       
    
    /**
     * Obtiene la fecha de penitencia
     * @return la fecha de penitencia
     */
    public Date getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    
    /**
     * Cambia la fecha de penitencia
     * @param fechaPenitencia la nueva fecha de penitencia
     */
    public void setFechaPenitencia(Date fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    
  
    
    public Collection<FechaEntity> getFechasOcupadas()
    {
        return fechasOcupadas;
    }
    
    public void setFechasOcupadas(Collection<FechaEntity> fechasOcupadas)
    {
        this.fechasOcupadas=fechasOcupadas;
    }
    
    public ProveedorEntity getProveedor()
    {
        return proveedor;
    }
    
   
    
}
