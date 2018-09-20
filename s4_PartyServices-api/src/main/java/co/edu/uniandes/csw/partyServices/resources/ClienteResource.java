/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;
import co.edu.uniandes.csw.partyServices.dtos.ClienteDetailDTO;

import co.edu.uniandes.csw.partyServices.dtos.ClienteDTO;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *Clase que implementa el recurso "Clientes"
 * @author Elias David Negrete Salgado
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource {
    
     @Inject
    private ClienteLogic clienteLogic;
    
        private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    
    @GET
    @Path("{clientesId: \\d+}")
    public ClienteDTO getCliente(@PathParam("clientesId") Long clienteId) throws BusinessLogicException{

        LOGGER.log(Level.INFO, "ClienteResource getBook: input: {0}", clienteId);
        ClienteEntity clienteEntity = clienteLogic.getCliente(clienteId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /books/" + clienteId + " no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource getBook: output: {0}", clienteDetailDTO.toString());
        return clienteDetailDTO;
        
    }
    /**
     * Busca y devuelve todos los libros que existen en la aplicacion.
     *
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        LOGGER.info("BookResource getBooks: input: void");
        List<ClienteDetailDTO> listaCliente = listEntity2DetailDTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "BookResource getBooks: output: {0}", listaCliente.toString());
        return listaCliente;
    }
    
    
    @POST
    @Path("{clientesId: \\d+}")
    public ClienteDTO createCliente(ClienteDTO pCliente) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "BookResource createBook: input: {0}", pCliente.toString());
        ClienteDTO nuevoBookDTO = new ClienteDTO(clienteLogic.createCliente(pCliente.toEntity()));
        LOGGER.log(Level.INFO, "BookResource createBook: output: {0}", nuevoBookDTO.toString());
        return nuevoBookDTO;   
    }
    
    
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDTO modificarCliente(@PathParam("clientesId") Long clienteId, ClienteDTO pCliente) throws BusinessLogicException{
    
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: input: id: {0} , book: {1}", new Object[]{clienteId, pCliente.toString()});
        pCliente.setId(clienteId);
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /books/" + clienteId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente( pCliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    @DELETE
    @Path("{clientesId: \\d+}")
    public void deleteCliente(@PathParam("clientesId") Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BookResource deleteBook: input: {0}", clienteId);
        ClienteEntity entity = clienteLogic.getCliente(clienteId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        //bookEditorialLogic.removeEditorial(clienteId);
        clienteLogic.deleteCliente(clienteId);
        LOGGER.info("BookResource deleteBook: output: void");
    }
    
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
    
    
     @Path("{clientesId: \\d+}/pagos")
    public Class<PagoResource> getPagoResource(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/pagos no existe.", 404);
        }
        return PagoResource.class;
    }
    
    
}
