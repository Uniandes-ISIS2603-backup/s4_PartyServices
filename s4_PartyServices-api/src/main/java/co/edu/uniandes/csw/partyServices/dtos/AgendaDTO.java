/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AgendaDTO Objeto de transferencia de datos de Agenda.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "fechaPenitencia": string,
 *      "proveedorDTO": ProveedorDTO,
 *      "jornadaLunesND": string,
 *      "jornadaMartesND": string,
 *      "jornadaMiercolesND": string,
 *      "jornadaJuevesND": string,
 *      "jornadaViernesND": string,
 *      "jornadaSabadoND": string,
 *      "jornadaDomingoND": string
 *
 *   }
 * </pre> Por ejemplo una agenda se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "fechaPenitencia": "2018-11-11T00:00:00-05:00"
 *      "proveedorDTO": {},
 *      "jornadaLunesND": "Ninguna",
 *      "jornadaMartesND": "Ninguna",
 *      "jornadaMiercolesND": "Ninguna",
 *      "jornadaJuevesND": "Ninguna",
 *      "jornadaViernesND": "Ninguna",
 *      "jornadaSabadoND": "Ninguna",
 *      "jornadaDomingoND": "Ninguna"
 *   }
 *
 * </pre>
 * @author Nicolas Hernandez
 */
public class AgendaDTO implements Serializable{
    /**
     * id de la agenda
     */
    private long id;
    
    /**
     * Fecha de penitencia que tiene el proveedor
     */
    private Date fechaPenitencia;
    
    /**
     * El proveedor de la agenda
     */
    private ProveedorDTO proveedorDTO;
    
    /**
     * Jornada de los lunes que el proveedor no trabaja
     */
    private String jornadaLunesND;
    
    /**
     * Jornada de los martes que el proveedor no trabaja
     */
    private String jornadaMartesND;
    
    /**
     * Jornada de los miercoles que el proveedor no trabaja
     */
    private String jornadaMiercolesND;
    
    /**
     * Jornada de los jueves que el proveedor no trabaja
     */
    private String jornadaJuevesND;
    
    /**
     * Jornada de los viernes que el proveedor no trabaja
     */
    private String jornadaViernesND;
    
    /**
     * Jornada de los sabados que el proveedor no trabaja
     */
    private String jornadaSabadoND;
    
    /**
     * Jornada de los domingos que el proveedor no trabaja
     */
    private String jornadaDomingoND;
    
    /**
     * Constructor por defecto
     */
    public AgendaDTO()
    {
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param agendaEntity: Es la entidad que se va a convertir a DTO
     */
    public AgendaDTO(AgendaEntity agendaEntity){
        if(agendaEntity!=null){
            this.id=agendaEntity.getId();
            this.fechaPenitencia=agendaEntity.getFechaPenitencia();
            this.proveedorDTO=new ProveedorDTO(agendaEntity.getProveedor());
            this.jornadaLunesND=agendaEntity.getJornadaLunesND();
            this.jornadaMartesND=agendaEntity.getJornadaMartesND();
            this.jornadaMiercolesND=agendaEntity.getJornadaMiercolesND();
            this.jornadaJuevesND=agendaEntity.getJornadaJuevesND();
            this.jornadaViernesND=agendaEntity.getJornadaViernesND();
            this.jornadaSabadoND=agendaEntity.getJornadaSabadoND();
            this.jornadaDomingoND=agendaEntity.getJornadaDomingoND();
        }
    }

    /**
     * Devuelve el id de la agenda
     * @return el id de la agenda
     */
    public long getId() 
    {
        return id;
    }

    /**
     * Cambia el id de la agenda
     * @param id  el id de la agenda
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Devuelve la jornada del los lunes que no labora
     * @return la jornada en que no labora los lunes
     */
    public String getJornadaLunesND() {
        return jornadaLunesND;
    }

    /**
     * Cambia la jornada que no labora los lunes
     * @param jornadaLunesND la jornada en que no labora los lunes
     */
    public void setJornadaLunesND(String jornadaLunesND) {
        this.jornadaLunesND = jornadaLunesND;
    }

    /**
     * Devuelve la jornada del los martes que no labora
     * @return la jornada en que no labora los martes
     */
    public String getJornadaMartesND() {
        return jornadaMartesND;
    }

    /**
     * Cambia la jornada que no labora los martes
     * @param jornadaMartesND la jornada en que no labora los martes
     */
    public void setJornadaMartesND(String jornadaMartesND) {
        this.jornadaMartesND = jornadaMartesND;
    }

    /**
     * Devuelve la jornada del los miercoles que no labora
     * @return la jornada en que no labora los miercoles
     */
    public String getJornadaMiercolesND() {
        return jornadaMiercolesND;
    }

    /**
     * Cambia la jornada que no labora los miercoles
     * @param jornadaMiercolesND la jornada en que no labora los miercoles
     */
    public void setJornadaMiercolesND(String jornadaMiercolesND) {
        this.jornadaMiercolesND = jornadaMiercolesND;
    }

    /**
     * Devuelve la jornada del los jueves que no labora
     * @return la jornada en que no labora los jueves
     */
    public String getJornadaJuevesND() {
        return jornadaJuevesND;
    }

    /**
     * Cambia la jornada que no labora los jueves
     * @param jornadaJuevesND la jornada en que no labora los jueves
     */
    public void setJornadaJuevesND(String jornadaJuevesND) {
        this.jornadaJuevesND = jornadaJuevesND;
    }

    /**
     * Devuelve la jornada del los viernes que no labora
     * @return la jornada en que no labora los viernes
     */
    public String getJornadaViernesND() {
        return jornadaViernesND;
    }

    /**
     * Cambia la jornada que no labora los viernes
     * @param jornadaViernesND la jornada en que no labora los viernes
     */
    public void setJornadaViernesND(String jornadaViernesND) {
        this.jornadaViernesND = jornadaViernesND;
    }

    /**
     * Devuelve la jornada del los sabados que no labora
     * @return la jornada en que no labora los sabados
     */
    public String getJornadaSabadoND() {
        return jornadaSabadoND;
    }

    /**
     * Cambia la jornada que no labora los sabados
     * @param jornadaSabadoND la jornada en que no labora los sabados
     */
    public void setJornadaSabadoND(String jornadaSabadoND) {
        this.jornadaSabadoND = jornadaSabadoND;
    }

    /**
     * Devuelve la jornada del los domingos que no labora
     * @return la jornada en que no labora los domingos
     */
    public String getJornadaDomingoND() {
        return jornadaDomingoND;
    }

    /**
     * Cambia la jornada que no labora los domingos
     * @param jornadaDomingoND la jornada en que no labora los domingos
     */
    public void setJornadaDomingoND(String jornadaDomingoND) {
        this.jornadaDomingoND = jornadaDomingoND;
    }

    
    /**
     * Obtiene el proveedor de la agenda
     * @return el proveedor de la agenda
     */
    public ProveedorDTO getProveedorDTO() {
        return proveedorDTO;
    }

    /**
     * Cambia el proveedor de la agenda
     * @param proveedorDTO  el proveedor de la agenda
     */
    public void setProveedorDTO(ProveedorDTO proveedorDTO) {
        this.proveedorDTO = proveedorDTO;
    }
    
    
    
   
    public Date getFechaPenitencia()
    {
        return fechaPenitencia;
    }
   
    public void setFechaPenitencia(Date fechaPenitencia)
    {
        this.fechaPenitencia=fechaPenitencia;
    }
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public AgendaEntity toEntity()
    {
        AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setId(this.id);
        agendaEntity.setFechaPenitencia(this.fechaPenitencia);
        if(this.proveedorDTO!=null) agendaEntity.setProveedor(this.proveedorDTO.toEntity());
        agendaEntity.setJornadaLunesND(this.jornadaLunesND);
        agendaEntity.setJornadaMartesND(this.jornadaMartesND);
        agendaEntity.setJornadaMiercolesND(this.jornadaMiercolesND);
        agendaEntity.setJornadaJuevesND(this.jornadaJuevesND);
        agendaEntity.setJornadaViernesND(this.jornadaViernesND);
        agendaEntity.setJornadaSabadoND(this.jornadaSabadoND);
        agendaEntity.setJornadaDomingoND(this.jornadaDomingoND);
        return agendaEntity;
       
    }
}
