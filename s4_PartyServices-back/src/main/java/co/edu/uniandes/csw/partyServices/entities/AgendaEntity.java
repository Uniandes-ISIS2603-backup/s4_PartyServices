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
 *
 * @author estudiante
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable{
   
    
    /**
     * DD:MM:AAAA
     */
    @Temporal(TemporalType.DATE)
    private Date fechaPenitencia;

    
    private String jornadaLunesND;
    private String jornadaMartesND;
    private String jornadaMiercolesND;
    private String jornadaJuevesND;
    private String jornadaViernesND;
    private String jornadaSabadoND;
    private String jornadaDomingoND;
    
    
    @PodamExclude
    @OneToMany(
            mappedBy="agenda",
            cascade = CascadeType.ALL, 
            orphanRemoval =true ,
            //Terminan en many son lazy. Eger terminan en One
            fetch= FetchType.LAZY
    )
    private Collection<FechaEntity> fechasOcupadas;
    
    @PodamExclude
    @OneToOne(
            mappedBy="agenda",
            fetch = FetchType.EAGER
    )
    private ProveedorEntity proveedor;

    public String getJornadaLunesND() {
        return jornadaLunesND;
    }

    public void setJornadaLunesND(String jornadaLunesND) {
        this.jornadaLunesND = jornadaLunesND;
    }

    public String getJornadaMartesND() {
        return jornadaMartesND;
    }

    public void setJornadaMartesND(String jornadaMartesND) {
        this.jornadaMartesND = jornadaMartesND;
    }

    public String getJornadaMiercolesND() {
        return jornadaMiercolesND;
    }

    public void setJornadaMiercolesND(String jornadaMiercolesND) {
        this.jornadaMiercolesND = jornadaMiercolesND;
    }

    public String getJornadaJuevesND() {
        return jornadaJuevesND;
    }

    public void setJornadaJuevesND(String jornadaJuevesND) {
        this.jornadaJuevesND = jornadaJuevesND;
    }

    public String getJornadaViernesND() {
        return jornadaViernesND;
    }

    public void setJornadaViernesND(String jornadaViernesND) {
        this.jornadaViernesND = jornadaViernesND;
    }

    public String getJornadaSabadoND() {
        return jornadaSabadoND;
    }

    public void setJornadaSabadoND(String jornadaSabadoND) {
        this.jornadaSabadoND = jornadaSabadoND;
    }

    public String getJornadaDomingoND() {
        return jornadaDomingoND;
    }

    public void setJornadaDomingoND(String jornadaDomingoND) {
        this.jornadaDomingoND = jornadaDomingoND;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }
             
    
    
    public Date getFechaPenitencia()
    {
        return fechaPenitencia;
    }
    
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
    
    public void setProveeedor(ProveedorEntity proveedor)
    {
        this.proveedor=proveedor;
    }
    
}
