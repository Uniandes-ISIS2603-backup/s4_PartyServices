package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.PagoDTO;
import co.edu.uniandes.csw.partyServices.ejb.PagoLogic;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "pagos".
 *
 * @author ELias Negrete
 * @version 1.0
 */
@Produces("application/json")
@Consumes("application/json")
public class PagoResource {

    private static final Logger LOGGER = Logger.getLogger(PagoResource.class.getName());

    @Inject
    private PagoLogic pagoLogic;

    /**
     * Crea una nuevo pago con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param pagosId El ID del cliente del cual se le agrega la pago
     * @param pago {@link PagoDTO} - La pago que se desea guardar.
     * @return JSON {@link PagoDTO} - La pago guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la pago.
     */
    @POST
    public PagoDTO createPago(@PathParam("clientesId") Long pagosId, PagoDTO pago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource create: input: {0}", pago.toString());
        PagoDTO nuevoDTO = new PagoDTO(pagoLogic.createPago(pagosId, pago.toEntity()));
        LOGGER.log(Level.INFO, "Resource create: output: {0}", nuevoDTO.toString());
        return nuevoDTO;
    }

    /**
     * Busca y devuelve todas las pagos que existen en un cliente.
     *
     * @param clientesId El ID del libro del cual se buscan las reseñas
     * @return JSONArray {@link PagoDTO} - Las reseñas encontradas en el libro.
     * Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PagoDTO> getPagos(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "Resource get: input: {0}", clientesId);
        List<PagoDTO> listaDTOs = listEntity2DTO(pagoLogic.getPagos(clientesId));
        LOGGER.log(Level.INFO, "Resource get: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    /**
     * Busca y devuelve la pago con el ID recibido en la URL, relativa a un
     * cliente.
     *
     * @param clientesId El ID del cliente del cual se buscan las pagos
     * @param pagosId El ID de la pago que se busca
     * @return {@link PagoDTO} - La pago encontradas en el cliente.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pago.
     */
    @GET
    @Path("{pagosId: \\d+}")
    public PagoDTO getPago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource get: input: {0}", pagosId);
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/pagos/" + pagosId + " no existe.", 404);
        }
        PagoDTO reviewDTO = new PagoDTO(entity);
        LOGGER.log(Level.INFO, "Resource get: output: {0}", reviewDTO.toString());
        return reviewDTO;
    }

    /**
     * Actualiza un pago con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param clientesId El ID del cliente del cual se guarda la reseña
     * @param pagosId El ID de la pago que se va a actualizar
     * @param pago {@link PagoDTO} - La pago que se desea guardar.
     * @return JSON {@link PagoDTO} - La pago actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la pago.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pago.
     */
    @PUT
    @Path("{pagosId: \\d+}")
    public PagoDTO updatePago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId, PagoDTO pago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource update: input: clientesId: {0} , pagosId: {1} , pago:{2}", new Object[]{clientesId, pagosId, pago.toString()});
        if (pagosId.equals(pago.getId())) {
            throw new BusinessLogicException("Los ids del pago no coinciden.");
        }
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/pagos/" + pagosId + " no existe.", 404);

        }
        PagoDTO DTO = new PagoDTO(pagoLogic.updatePago(clientesId, pago.toEntity()));
        LOGGER.log(Level.INFO, "Resource update: output:{0}", DTO.toString());
        return DTO;

    }

    /**
     * Borra el pago con el id asociado recibido en la URL.
     *
     * @param clientesId El ID del cliente del cual se va a eliminar la reseña.
     * @param pagosId El ID de la pago que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la pago.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pago.
     */
    @DELETE
    @Path("{pagosId: \\d+}")
    public void deleteReview(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/pagos/" + pagosId + " no existe.", 404);
        }
        pagoLogic.deletePago(clientesId, pagosId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PagoEntity a una lista de
     * objetos PagoDTO (json)
     *
     * @param entityList corresponde a la lista de pagos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de pagos en forma DTO (json)
     */
    private List<PagoDTO> listEntity2DTO(List<PagoEntity> entityList) {
        List<PagoDTO> list = new ArrayList<PagoDTO>();
        for (PagoEntity entity : entityList) {
            list.add(new PagoDTO(entity));
        }
        return list;
    }
}
