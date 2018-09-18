/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ProductoLogic {

    private static final Logger LOGGER = Logger.getLogger(ProductoLogic.class.getName());

    @Inject
    private ProductoPersistence persistence;

    public ProductoEntity getProducto(String pNombre) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto por nombre");
        ProductoEntity productoEntity = persistence.findByName(pNombre);
        if (productoEntity == null) {
            LOGGER.log(Level.SEVERE, "El libro con el nombre dado no existe ");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar producto por nombre");
        return productoEntity;

    }

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
       if (validateNombreCaracteres(productoEntity.getNombre())) 
       {
            throw new BusinessLogicException("El nombre del producto contiene caracteres especiales");
        }
       if (productoEntity.getDuenio() == null || productoEntity.getProveedor() == null) {
            throw new BusinessLogicException("El producto debe tener un proveedor asociado");
        }
        if (productoEntity.getCosto() < 0 || productoEntity.getCosto() >= 2147483647) {
            throw new BusinessLogicException("El producto debe tener un costo entre 0 y 214783647");
        }
        if (productoEntity.getCantidad() < 0 || productoEntity.getCantidad() >= 999) {
            throw new BusinessLogicException("El producto debe tener una cantidad entre 0 y 999");
        }
        if (productoEntity.getEventos() != null) {
            if (productoEntity.getEventos().size() > productoEntity.getCantidad()) {
                throw new BusinessLogicException("La cantidad de eventos en un producto no puede ser mayor a la cantidad de productos");
            }
        }

        persistence.create(productoEntity);

        LOGGER.log(Level.INFO, "Termino proceso de creación del producto");

        return productoEntity;

    }

    public boolean validateNombre(String pNombre) {
        return !(pNombre == null || pNombre.isEmpty());
    }

    public void deleteProducto(String pNombre) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación del producto");

        ProductoEntity buscado = persistence.findByName(pNombre);

        if (buscado == null) {
            throw new BusinessLogicException("No existen productos con el nombre solicitado");
        } else if (buscado.getEventos().size() > 0) {
            throw new BusinessLogicException("No es posible borrar el producto solicitado porque aun tiene eventos asociados");
        }

        persistence.delete(buscado.getId());

        LOGGER.log(Level.INFO, "Termina proceso de eliminación del producto");

    }

    public ProductoEntity updateProducto(String pNombre, ProductoEntity pProductoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Comienza proceso de actualizacion del producto");

        if (!(validateNombre(pNombre))) {
            throw new BusinessLogicException("El producto del producto es inválido");
        }
        ProductoEntity newEntity = persistence.update(pProductoEntity);

        LOGGER.log(Level.INFO, "termina proceso de actualizacion del producto");
        return newEntity;
    }

    private boolean validateNombreCaracteres(String nombre) 
    {
        Pattern pat = Pattern.compile("[a-zA-Z]");
        Matcher mat = pat.matcher(nombre);
        return (mat.matches());
    }
    
    
    
}