
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ClienteDTO;
import co.edu.uniandes.csw.partyServices.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.ClienteLogic;
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;


@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    @Inject
    private ClienteLogic clienteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un nuevo cliente con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param cliente {@link ClienteDTO} - El cliente que se desea guardar.
     * @return JSON {@link ClienteDTO} - El cliente guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException
     */
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource create: input: {0}", cliente.toString());

        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ClienteEntity entity = cliente.toEntity();
        // Invoca la lógica para crear el cliente nuevo
        ClienteEntity nuevoEntity = clienteLogic.createCliente(entity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ClienteDTO nuevoDTO = new ClienteDTO(nuevoEntity);
        
        LOGGER.log(Level.INFO, "Resource create: output: {0}", nuevoDTO.toString());
        return nuevoDTO;
    }

    /**
     * Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        LOGGER.info("BookResource getBooks: input: void");
        List<ClienteDetailDTO> listaCliente = listEntity2DetailDTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "BookResource getBooks: output: {0}", listaCliente.toString());
        return listaCliente;
    }

    /**
     * Busca el cliente con el id asociado recibido en la URL y lo devuelve.
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @GET
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "Resource get: input: {0}", clientesId);
        ClienteEntity entity = clienteLogic.getCliente(clientesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(entity);
        LOGGER.log(Level.INFO, "Resource get: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el cliente con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param clientesId Identificador del cliente libro que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param cliente {@link ClienteDTO} El cliente que se desea guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el cliente.
     */
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clientesId") Long clientesId, ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource update: input: id: {0} , cliente: {1}", new Object[]{clientesId, cliente.toString()});
        cliente.setId(clientesId);
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente(clientesId, cliente.toEntity()));
        LOGGER.log(Level.INFO, "Resource update: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra el cliente con el id asociado recibido en la URL.
     *
     * @param clientesId Identificador del libro que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException 
     * cuando el libro tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @DELETE
    @Path("{clientesId: \\d+}")
    public void deleteCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Resource delete: input: {0}", clientesId);
       ClienteEntity entity = clienteLogic.getCliente(clientesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        clienteLogic.deleteCliente(clientesId);
        LOGGER.info("Resource delete: output: void");
    }

    /**
     * Conexión con el servicio de pagos para un cliente. {@link PagoResource}
     *
     * Este método conecta la ruta de /clientes con las rutas de /pagos que
     * dependen del cliente, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los pagos.
     *
     * @param clientesId El ID del libro con respecto al cual se accede al
     * servicio.
     * @return El servicio de pagos para ese cliente en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @Path("{clientesId: \\d+}/pagos")
    public Class<PagoResource> getPagoResource(@PathParam("clientesId") Long clientesId) {
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/pagos no existe.", 404);
        }
        return PagoResource.class;
    }

   
    

   /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ClienteEntity a una lista de
     * objetos CliennnteDetailDTO (json)
     *
     * @param entityList corresponde a la lista de clientes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de clientes en forma DTO (json)
     */
    private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
}
