package com.blastic.prometheus.webapi.rest.resource;

import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodRequest;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.service.NeighborhoodService;
import com.blastic.prometheus.webapi.service.ServiceFactory;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("neighborhoods")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class NeighborhoodResource {

    @Context
    private HttpServletRequest request;

    private final NeighborhoodService service = ServiceFactory
            .getNeighborhoodService();

    @GET
    public ListResponse<NeighborhoodResponse> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size, @QueryParam("search") String search) {
        if (size == null)
            size = 20;
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(start, size, search);
    }

    @GET
    @Path("{id}")
    public NeighborhoodResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(NeighborhoodRequest data,
            @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        NeighborhoodResponse response = service.add(data);
        return Response.created(uriInfo.getAbsolutePath())
                .entity(response).build();
    }

    @PUT
    @Path("{id}")
    public NeighborhoodResponse put(@PathParam("id") Long id,
            NeighborhoodRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }

    @DELETE
    @Path("{id}")
    public NeighborhoodResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
}
