/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.ProveedorDTO;
import co.edu.uniandes.csw.partyServices.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.ProveedorLogic;
import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
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
 *
 * @author estudiante
 */
@Path("proveedor")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProveedorResource {

    private static final Logger LOGGER = Logger.getLogger(ProveedorResource.class.getName());
     @Inject
    private ProveedorLogic proveedorLogic;
     
    @GET
    public List<ProveedorDetailDTO> getProveedores() throws BusinessLogicException {
          LOGGER.info("ProveedorResource getProveedores: input: void");
        List<ProveedorDetailDTO> listaProveedores = listEntity2DTO(proveedorLogic.getProveedores());
        LOGGER.log(Level.INFO, "AuthorResource getAuthors: output: {0}", listaProveedores.toString());
        return listaProveedores;
    }

    @GET
    @Path("{proveedoresId: \\d+}")
    public ProveedorDetailDTO getProveedor(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "ProveedorResource getAuthor: input: {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorLogic.getProveedor(proveedoresId);
        if (proveedorEntity == null) {
            throw new WebApplicationException("El recurso /proveedores/" + proveedoresId + " no existe.", 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorEntity);
        LOGGER.log(Level.INFO, "ProveedorResource getProveedor: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @GET
    @Path("{nombre: ([0-9]|[a-zA-Z])([0-9]|[a-zA-Z])*}/{contrasenia: ([0-9]|[a-zA-Z])([0-9]|[a-zA-Z])*}")
    public ProveedorDetailDTO validate(@PathParam("nombre") String nombre, @PathParam("contrasenia") String contrasenia) {
        LOGGER.log(Level.INFO, "ProveedorResource getAuthor: input: {0}", nombre);
        ProveedorEntity proveedorEntity = proveedorLogic.validate(nombre, contrasenia);
        if (proveedorEntity == null) {
            throw new WebApplicationException("El recurso /proveedores/" + nombre + " no existe.", 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorEntity);
        LOGGER.log(Level.INFO, "ProveedorResource getProveedor: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
                
    @POST
    public ProveedorDTO crearProveedor(ProveedorDTO pProveedor) throws BusinessLogicException {
       LOGGER.log(Level.INFO, "ProveedorResource createProveedor: input: {0}", pProveedor.toString());
        ProveedorDTO proveedorDTO = new ProveedorDTO(proveedorLogic.createProveedor(pProveedor.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorResource createProveedor: output: {0}", proveedorDTO.toString());
        return proveedorDTO;
    }

    @DELETE
    @Path("{proveedoresId: \\d+}")
    public void borrarProveedor(@PathParam("proveedoresId") Long proveedoresID) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorResource deleteProveedor: input: {0}", proveedoresID);
        if (proveedorLogic.getProveedor(proveedoresID) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + proveedoresID + " no existe.", 404);
        }
        proveedorLogic.deleteProveedor(proveedoresID);
        LOGGER.info("AuthorResource deleteAuthor: output: void");
    }

    @PUT
    @Path("{proveedoresId: \\d+}")
    public ProveedorDTO actualizarProveedor(@PathParam("proveedoresId") Long proveedoresId, ProveedorDTO proveedor) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "ProveedorResource updateProveedor: input: proveedoresId: {0} , proveedor: {1}", new Object[]{proveedoresId, proveedor.toString()});
        proveedor.setId(proveedoresId);
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + proveedoresId + " no existe.", 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorLogic.updateProveedor(proveedoresId, proveedor.toEntity()));
        LOGGER.log(Level.INFO, "AuthorResource updateAuthor: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
        private List<ProveedorDetailDTO> listEntity2DTO(List<ProveedorEntity> entityList) {
        List<ProveedorDetailDTO> list = new ArrayList<>();
        for (ProveedorEntity entity : entityList) {
            list.add(new ProveedorDetailDTO(entity));
        }
        return list;
    }
        
    /**
     * Conexión con el servicio de valroaciones para un proveedor. {@link SugerenciaResource}
     *
     * Este método conecta la ruta de /proveedores con las rutas de /valroaciones que
     * dependen del proveedor, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las sugerencias.
     *
     * @param proveedorId El ID del proveedor con respecto a la cual se accede al
     * servicio.
     * @return El servicio de valoraciones para ese proveedor en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proveedor.
     */
    @Path("{proveedorId: \\d+}/valoraciones")
    public Class<ValoracionResource> getValoracionResource(@PathParam("proveedorId") Long proveedorId) {
        if (proveedorLogic.getProveedor(proveedorId) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + proveedorId + "/valoraciones no existe.", 404);
        }
        return ValoracionResource.class;
    }

}
