
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.dtos;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Andres
 */
public class ProductoDTO implements Serializable {

    /**
     * Id del producto
     */
    private Long id;

    /**
     * Nombre del producto
     */
    private String nombre;

    /**
     * Tipo de servicio al que pertenece el producto
     */
    private String tipoServicio;

    /**
     * Nombre del dueño del producto
     */
    private String duenio;

    /**
     * Costo del producto
     */
    private Integer costo;

    /**
     * Cantidad disponible del producto
     */
    private Integer cantidad;

    /**
     * Proveedor dueño del producto
     */
    private ProveedorDTO proveedor;

    /**
     * Constructor por defecto
     */
    public ProductoDTO() {

    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param productoEntity Es la entidad que se va a convertir a DTO
     */
    public ProductoDTO(ProductoEntity productoEntity) {
        if (productoEntity != null) {
            this.id = productoEntity.getId();
            this.nombre = productoEntity.getNombre();
            this.tipoServicio = productoEntity.getTipoServicio();
            this.duenio = productoEntity.getDuenio();
            this.costo = productoEntity.getCosto();
            this.cantidad = productoEntity.getCantidad();
            this.proveedor = new ProveedorDTO(productoEntity.getProveedor());

        }
    }

    /**
     * Convierte el DTO actual a un entity
     *
     * @return un objeto Entity de producto
     */
    public ProductoEntity toEntity() {
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setId(this.getId());
        productoEntity.setNombre(this.getNombre());
        productoEntity.setTipoServicio(this.tipoServicio);
        productoEntity.setDuenio(this.duenio);
        productoEntity.setCosto(this.costo);
        productoEntity.setCantidad(this.cantidad);
        productoEntity.setProveedor(this.proveedor.toEntity());

        return productoEntity;
    }

    /**
     * Retorna el id del producto
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id del producto
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre del producto
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del producto
     *
     * @param pNombre
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }

    /**
     * Retorna el tipo de servicio del producto
     *
     * @return tipoServicio
     */
    public String getTipoServicio() {
        return tipoServicio;
    }

    /**
     * Modifica el tipo de servicio del producto
     *
     * @param pTipoServicio
     */
    public void setTipoServicio(String pTipoServicio) {
        this.tipoServicio = pTipoServicio;
    }

    /**
     * Retorna el nombre del dueño del producto
     *
     * @return duenio
     */
    public String getDuenio() {
        return duenio;
    }

    /**
     * Modifica el nombre del dueño del producto
     *
     * @param pDuenio
     */
    public void setDuenio(String pDuenio) {
        this.duenio = pDuenio;
    }

    /**
     * Retorna el costo del producto
     *
     * @return costo
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * Modifica el costo de un producto
     *
     * @param pCosto
     */
    public void setCosto(Integer pCosto) {
        this.costo = pCosto;
    }

    /**
     * Retorna la cantidad disponible del producto
     *
     * @return cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad actual de productos disponibles
     *
     * @param pCantidad
     */
    public void setCantidad(Integer pCantidad) {
        this.cantidad = pCantidad;
    }

    /**
     * Retorna el proveedor dueño del producto
     *
     * @return proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * Modifica el proveedor del producto
     *
     * @param proveedor
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Metodo ToString
     *
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
