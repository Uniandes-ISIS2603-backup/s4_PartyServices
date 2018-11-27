/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProveedorPersistence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres
 */
@Stateless
public class ProductoLogic {

    private static final Logger LOGGER = Logger.getLogger(ProductoLogic.class.getName());

    /**
     * Persistencia de producto
     */
    @Inject
    private ProductoPersistence persistence;
    
    /**
     * Persistencia de proveedor
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;

    /**
     * obtener un producto por nombre
     * @param pNombre nombre del producto que se quiere consultar
     * @return ptoducto con el nombre solicitado
     */
    public ProductoEntity getProducto(String pNombre) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto por nombre");
        ProductoEntity productoEntity = persistence.findByName(pNombre);
        if (productoEntity == null) {
            LOGGER.log(Level.SEVERE, "El libro con el nombre dado no existe ");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar producto por nombre");
        return productoEntity;

    }

    /**
     * Crear un producto
     * @param productoEntity producto que se quiere crear
     * @return el producto creado y validado
     * @throws BusinessLogicException si no se cumplen las reglase de negocio
     */
    public ProductoEntity createProducto(ProductoEntity productoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del producto");

        if (!(validateNombre(productoEntity.getNombre()))) {
            throw new BusinessLogicException("El nombre del producto debe ser diferente de null y no puede ser vacio");
        }
        if (persistence.findByName(productoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un producto con ese nombre");
        }
        if (productoEntity.getNombre().length() < 3 || productoEntity.getNombre().length() > 35) {
            throw new BusinessLogicException("El nombre del producto debe de tener entre 3 y 35 caracteres");
        }
        if (validateNombreCaracteres(productoEntity.getNombre())) {
            throw new BusinessLogicException("El nombre del producto contiene caracteres especiales");
        }
        if (productoEntity.getDuenio() == null || productoEntity.getProveedor() == null ) 
        {
            throw new BusinessLogicException("El producto debe tener un proveedor asociado");
        }
        if (productoEntity.getCosto() < 0 || productoEntity.getCosto() >= 2147483647) {
            throw new BusinessLogicException("El producto debe tener un costo entre 0 y 214783647");
        }
        if (productoEntity.getCantidad() < 0 || productoEntity.getCantidad() >= 999) {
            throw new BusinessLogicException("El producto debe tener una cantidad entre 0 y 999");
        }
        if (productoEntity.getEventos() == null) 
        {
            productoEntity.setEventos(new ArrayList<>());

        }
        if (productoEntity.getEventos().size() > productoEntity.getCantidad()) {
            throw new BusinessLogicException("La cantidad de eventos en un producto no puede ser mayor a la cantidad de productos");
        }
        
        ProveedorEntity proveedor = proveedorPersistence.find(productoEntity.getProveedor().getId());
        
        
        if(proveedor == null)
        {
            throw new BusinessLogicException("El producto debe tener un proveedor existente");
        }
        Collection<ProductoEntity> lista = proveedor.getCatalogoProductos() ;
       
        if(lista == null)
        {
            lista = new ArrayList<>() ;
        }
 
        lista.add(productoEntity) ;
        
        proveedor.setCatalogoProductos(lista);
       
        proveedor = proveedorPersistence.update(proveedor) ;
        
        productoEntity.setProveedor(proveedor);
        
        LOGGER.log(Level.INFO, "Termino proceso de creación del producto");

        return persistence.create(productoEntity);

    }

    /**
     * Valida que el nombre del producto no sea nulo ni este vacio
     * @param pNombre nombre a validar
     * @return true si cumple con los requisitos, false de lo contrario
     */
    public boolean validateNombre(String pNombre) {
        return !(pNombre == null || pNombre.isEmpty());
    }

    /**
     * Elimina un producto
     * @param pNombre nombre del producto que se quiere eliminar
     * @throws BusinessLogicException si las reglas de negocio no se cumplen
     */
    public void deleteProducto(String pNombre) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación del producto");

        ProductoEntity buscado = persistence.findByName(pNombre);

        if (buscado == null) {
            throw new BusinessLogicException("No existen productos con el nombre solicitado");
        } 
        else if (!buscado.getEventos().isEmpty()) 
        {
            throw new BusinessLogicException("No es posible borrar el producto solicitado porque aun tiene eventos asociados");
        }

        persistence.delete(buscado.getId());

        LOGGER.log(Level.INFO, "Termina proceso de eliminación del producto");

    }

    /**
     * Actualiza un producto
     * @param pNombre nombre del producto que se quiere actualizar
     * @param pProductoEntity objeto con la informacion nueva 
     * @return el producto actualizado
     * @throws BusinessLogicException si las reglas de nogcio no se cumplen
     */
    public ProductoEntity updateProducto(String pNombre, ProductoEntity pProductoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Comienza proceso de actualizacion del producto");

        if (!(validateNombre(pNombre))) {
            throw new BusinessLogicException("El producto del producto es inválido");
        }
        ProductoEntity newEntity = persistence.update(pProductoEntity);

        LOGGER.log(Level.INFO, "termina proceso de actualizacion del producto");
        return newEntity;
    }

    /**
     * Valida si el nombre del producto no contiene caracteres espceiales 
     * @param nombre nombre a verfificar
     * @return true si cumple con los requisitos, false de lo contrario
     */
    private boolean validateNombreCaracteres(String nombre) {
        Pattern pat = Pattern.compile("[a-zA-Z]");
        Matcher mat = pat.matcher(nombre);
        return (mat.matches());
    }

    /**
     * Encuentra un producto por su nombre
     * @param pNombre nombre del producto a consultar
     * @return producto 
     */
    public ProductoEntity findByNombre(String pNombre) 
    {
        return persistence.findByName(pNombre) ;
        
    }
    
    /**
     * Consulta todos los productos
     * @return una lista con todos los productos 
     */
    public List<ProductoEntity> findAll()
    {
        return persistence.findAll() ;
    }
    
    /**
     * Busca un libro por ID
     *
     * @param productosId El id del producto a buscar
     * @return El producto encontrado, null si no lo encuentra.
     * @author Tomas Vargas
     */
    public ProductoEntity getProductoId(Long productosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto con id = {0}", productosId);
        ProductoEntity productoEntity = persistence.find(productosId);
        if (productoEntity == null) {
            LOGGER.log(Level.SEVERE, "El producto con el id = {0} no existe", productosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0}", productosId);
        return productoEntity;
    }
}
