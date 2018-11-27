/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SugerenciaDTO Objeto de transferencia de datos de Valoraciones.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "titulo": string,
 *      "puntaje": number,
 *      "comentario": string,
 * |    "nombreUsuario": string
 *   }
 * </pre> Por ejemplo una valoracion se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "puntaje": 5,
 *      "titulo": "No me gustó",
 *      "comentario": "Globos de mala calidad",
 *      "nombreUsuario":"Jesus"
 *   }
 *
 * </pre>
 *
 * @author Jesús Orlando Cárcamo Posada
 */
public class ValoracionDTO implements Serializable {

    private Long id;
    private Integer puntaje;
    private String titulo;
    private String comentario;
    private String nombreUsuario;

    /**
     * Relación fantasma con proveedor no hace parte del UML pero no corría si
     * no la ponía, después se quitará
     */
    private ProveedorDTO proveedor;
    
    private ClienteDTO cliente;

    


    /**
     * Constructor por defecto
     */
    public ValoracionDTO() {
        //Constructor por defecto
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param valoracionEntity: Es la entidad que se va a convertir a DTO
     */
    public ValoracionDTO(ValoracionEntity valoracionEntity) {
        if (valoracionEntity != null) {
            this.id = valoracionEntity.getId();
            this.puntaje = valoracionEntity.getPuntaje();
            this.comentario = valoracionEntity.getComentario();
            this.nombreUsuario = valoracionEntity.getNombreUsuario();
            this.titulo = valoracionEntity.getTitulo();

            if (valoracionEntity.getProveedor() != null) {
                this.proveedor = new ProveedorDTO(valoracionEntity.getProveedor());
            } else {
                this.proveedor = null;
            }
            if (valoracionEntity.getCliente() != null) {
                this.cliente = new ClienteDTO(valoracionEntity.getCliente());
            } else {
                this.cliente = null;
            }
        }
    }

    /**
     * Devuelve el ID de la valoracion.
     *
     * @return id. El Identificador de esta valoracion.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la valoracion.
     *
     * @param id. El nuevo ID de la valoracion.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el puntaje de la valoracion.
     *
     * @return puntaje. El puntaje de la valoracion.
     */
    public Integer getPuntaje() {
        return puntaje;
    }

    /**
     * Modifica el puntaje de la valoracion.
     *
     * @param puntaje. El nuevo puntaje de la valoracion.
     */
    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
    
    
     /**
     * Devuelve el titulo de la valoracion.
     *
     * @return titulo2. El titulo de la valoracion.
     */
     public String getTitulo() {
        return titulo;
    }

     /**
     * Modifica el titulo de la valoracion.
     *
     * @param pTitulo2. El nuevo titulo de la valoracion.
     */
    public void setTitulo(String pTitulo2) {
        this.titulo = pTitulo2;
    }
    

    /**
     * Devuelve el comentario de la valoracion.
     *
     * @return comentario. El comentario de la valoracion.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la valoracion.
     *
     * @param comentario. El nuevo comentario de la valoracion.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Devuelve el nombre del usuario asignado a la valoracion.
     *
     * @return nombreUsuario. El nombre del usuario asignado a la valoracion.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el nombre del usuario asignado a la valoracion.
     *
     * @param nombreUsuario. El nuevo nombre del usuario que se reemplazará.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Devuelve el proveedor asociado a esta valoracion.
     *
     * @return proveedor. El proveedor dueño de la valoración.
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * Modifica el proveedor asociado a esta valoracion.
     *
     * @param proveedor. El proveedor que se reemplazará
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }
   

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ValoracionEntity toEntity() {
        ValoracionEntity valoracionEntity = new ValoracionEntity();
        valoracionEntity.setId(this.id);
        valoracionEntity.setPuntaje(this.puntaje);
        valoracionEntity.setComentario(this.comentario);
        valoracionEntity.setNombreUsuario(this.nombreUsuario);
        valoracionEntity.setTitulo(this.titulo);

        if (this.proveedor != null) {
            valoracionEntity.setProveedor(this.proveedor.toEntity());
        }
         if (this.cliente != null) {
            valoracionEntity.setCliente(this.cliente.toEntity());
        }
        return valoracionEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
