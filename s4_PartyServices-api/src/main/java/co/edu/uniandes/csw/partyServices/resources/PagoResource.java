/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.PagoDTO;
import co.edu.uniandes.csw.partyServices.ejb.PagoLogic;
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
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
 * Clase que implementa el recurso "Pagos"
 *
 * @author Elias David Negrete Salgado
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {

    private static final Logger LOGGER = Logger.getLogger(PagoResource.class.getName());

    @Inject
    private PagoLogic pagoLogic;

    @POST
    public PagoDTO createPago(@PathParam("clientesId") Long clientesId, PagoDTO pago) throws BusinessLogicException {
        PagoDTO nuevoDTO = new PagoDTO(pagoLogic.createPago(clientesId, pago.toEntity()));
        return nuevoDTO;
    }

    @GET
    @Path("{pagosId: \\d+}")
    public PagoDTO getReview(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /" + clientesId + "/" + pagosId + " no existe.", 404);
        }
        PagoDTO reviewDTO = new PagoDTO(entity);
        return reviewDTO;
    }

    @PUT
    @Path("{pagosId: \\d+}")
    public PagoDTO updatePago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId, PagoDTO pagoDTO) throws BusinessLogicException {
        if (pagosId.equals(pagoDTO.getId())) {
            throw new BusinessLogicException("Los ids no coinciden.");
        }
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /" + clientesId + "/" + pagosId + " no existe.", 404);

        }
        PagoDTO DTO = new PagoDTO(pagoLogic.updatePago(entity));
        return DTO;

    }

    @DELETE
    @Path("{pagosId: \\d+}")
    public void deletePago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        PagoEntity entity = pagoLogic.getPago(clientesId, pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso " + clientesId + "/" + pagosId + " no existe.", 404);
        }
        pagoLogic.deletePago(clientesId, pagosId);
    }

}
