/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.TarjetaCreditoDTO;
import co.edu.uniandes.csw.partyServices.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
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
 * Clase que implementa el recurso "TarjetaCredito".
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Produces("application/json")
@Consumes("application/json")
public class TarjetaCreditoResource {

    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoResource.class.getName());

    @Inject
    private TarjetaCreditoLogic tarjetaCreditoLogic;

    @POST
    public TarjetaCreditoDTO createTarjetaCredito(@PathParam("clientesId") Long clientesId, TarjetaCreditoDTO tarjetaCredito) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource createTarjetaCredito: input: {0}", tarjetaCredito);
        TarjetaCreditoDTO nuevoDTO = new TarjetaCreditoDTO(tarjetaCreditoLogic.createTarjetaCredito(clientesId, tarjetaCredito.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource createTarjetaCredito: output: {0}", nuevoDTO);
        return nuevoDTO;
    }

    @GET
    @Path("{tarjetaCreditoId: \\d+}")
    public TarjetaCreditoDTO getTarjetaCredito(@PathParam("clientesId") Long clientesId, @PathParam("tarjetaCreditoId") Long tarjetaCreditoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjetaCredito: input: id : {0}", tarjetaCreditoId);
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCredito(clientesId, tarjetaCreditoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito/" + tarjetaCreditoId + " no existe.", 404);
        }
        TarjetaCreditoDTO tarjetaCreditoDTO = new TarjetaCreditoDTO(entity);
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjetaCredito: output: cliente: {0}", tarjetaCreditoDTO);
        return tarjetaCreditoDTO;
    }
    
    @GET
    public TarjetaCreditoDTO getTarjetaCreditoXCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjetaCredito: input: clienteId : {0}", clientesId);
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCreditoByCliente(clientesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito" + " no existe.", 404);
        }
        TarjetaCreditoDTO tarjetaCreditoDTO = new TarjetaCreditoDTO(entity);
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjetaCredito: output: cliente: {0}", tarjetaCreditoDTO);
        return tarjetaCreditoDTO;
    }
    
    @PUT
    @Path("{tarjetaCreditoId: \\d+}")
    public TarjetaCreditoDTO updateTarjetaCredito(@PathParam("clientesId") Long clientesId, @PathParam("tarjetaCreditoId") Long tarjetaCreditoId, TarjetaCreditoDTO tarjetaCredito) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource update: input: clientesId: {0} , tarjetaCreditoId: {1} , tarjetaCredito:{2}", new Object[]{clientesId, tarjetaCreditoId, tarjetaCredito});
        
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCredito(clientesId, tarjetaCreditoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito/" + tarjetaCreditoId + " no existe.", 404);

        }
        TarjetaCreditoDTO tarjetaCreditoDTO = new TarjetaCreditoDTO(tarjetaCreditoLogic.updateTarjetaCredito(clientesId, tarjetaCredito.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource update: output:{0}", tarjetaCreditoDTO);
        return tarjetaCreditoDTO;

    }
    
    @PUT
    public TarjetaCreditoDTO updateTarjetaCreditoXCliente(@PathParam("clientesId") Long clientesId, TarjetaCreditoDTO tarjetaCredito) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource update: input: clientesId: {0} , tarjetaCredito:{2}", new Object[]{clientesId, tarjetaCredito});
        
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCreditoByCliente(clientesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito" + " no existe.", 404);

        }
        TarjetaCreditoDTO tarjetaCreditoDTO = new TarjetaCreditoDTO(tarjetaCreditoLogic.updateTarjetaCredito(clientesId, tarjetaCredito.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource update: output:{0}", tarjetaCreditoDTO);
        return tarjetaCreditoDTO;

    }
    
    @DELETE
    @Path("{tarjetaCreditoId: \\d+}")
    public void deleteTarjetaCredito(@PathParam("clientesId") Long clientesId, @PathParam("tarjetaCreditoId") Long tarjetaCreditoId) throws BusinessLogicException {
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCredito(clientesId, tarjetaCreditoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito/" + tarjetaCreditoId + " no existe.", 404);
        }
        tarjetaCreditoLogic.deleteTarjetaCredito(clientesId, tarjetaCreditoId);
    }
    
    @DELETE
    public void deleteTarjetaCreditoXCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCreditoByCliente(clientesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/tarjetaCredito" + " no existe.", 404);
        }
        tarjetaCreditoLogic.deleteTarjetaCreditoXCliente(clientesId);
    }

}
