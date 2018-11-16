package co.edu.uniandes.csw.partyServices.ejb;


import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.SugerenciaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de producto y sugerencia.
 *
 * @author Elias Negrete
 */
@Stateless
public class SugerenciaProductoLogic {

    private static final Logger LOGGER = Logger.getLogger(SugerenciaProductoLogic.class.getName());

    @Inject
    private ProductoPersistence productoPersistence;

    @Inject
    private SugerenciaPersistence sugerenciaPersistence;

    /**
     * Asocia un producto existente a una sugerencia
     *
     * @param tematicasId Identificador de la instancia de tematica
     * @param sugerenciaId Identificador de la instancia de sugerencia
     * @param productoId Identificador de la instancia de producto
     * @return Instancia de ProductoEntity que fue asociada a la sugerencia
     */
    public ProductoEntity addProducto(Long tematicasId,Long sugerenciaId, Long productoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un producto a la sugerencia con id = {0}", sugerenciaId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId,sugerenciaId);
        ProductoEntity productoEntity = productoPersistence.find(productoId);
        sugerenciaEntity.getProductos().add(productoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un producto a la sugerencia con id = {0}", sugerenciaId);
        return productoPersistence.find(productoId);
    }

    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una 
     * instancia de sugerencia
     * @param sugerenciaId Identificador de la instancia de sugerencia
     * @param tematicasId Identificador de la instancia de tematica
     * @return Colección de instancias de ProductoEntity asociadas a la instancia de sugerencia
     */
    public List<ProductoEntity> getProductos(Long tematicasId, Long sugerenciaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos de la sugerencia con id = {0}", sugerenciaId);
        return sugerenciaPersistence.find(tematicasId,sugerenciaId).getProductos();
    }

    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de sugerencia
     *
     * @param tematicasId Identificador de la instancia de tematicas
     * @param sugerenciaId Identificador de la instancia de sugerencia
     * @param productoId Identificador de la instancia de producto
     * @return La entidadd de producto de la sugerencia
     * @throws BusinessLogicException Si el producto no está asociado a la sugerencia
     */
    public ProductoEntity getProducto(Long tematicasId, Long sugerenciaId, Long productoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto con id = {0} de la sugerencia con id = " + sugerenciaId, productoId);
        List<ProductoEntity> productos = sugerenciaPersistence.find(tematicasId, sugerenciaId).getProductos();
        ProductoEntity productoEntity = productoPersistence.find(productoId);
        int index = productos.indexOf(productoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el producto con id = {0} de la sugerencia con id = " + sugerenciaId, productoId);
        if (index >= 0) {
            return productos.get(index);
        }
        throw new BusinessLogicException("El producto no está asociado a la sugerencia");
    }

  

    /**
     * Desasocia un producto existente de una sugerencia existente
     *
     * @param tematicasId Identificador de la instancia de tematica
     * @param sugerenciaId Identificador de la instancia de sugerencia
     * @param productoId Identificador de la instancia de producto
     */
    public void removeProducto(Long tematicasId, Long sugerenciaId, Long productoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un producto de la sugerencia con id = {0}", sugerenciaId);
        SugerenciaEntity sugerenciaEntity = sugerenciaPersistence.find(tematicasId, sugerenciaId);
        ProductoEntity productoEntity = productoPersistence.find(productoId);
        sugerenciaEntity.getProductos().remove(productoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un producto de la sugerencia con id = {0}", sugerenciaId);
    }
}
