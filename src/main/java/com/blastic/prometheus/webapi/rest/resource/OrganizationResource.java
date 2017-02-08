package com.blastic.prometheus.webapi.rest.resource;

import com.blastic.prometheus.webapi.model.dto.OrganizationRequest;
import com.blastic.prometheus.webapi.model.dto.OrganizationResponse;
import com.blastic.prometheus.webapi.service.OrganizationService;
import com.blastic.prometheus.webapi.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("organizations")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class OrganizationResource {

    @Context
    private HttpServletRequest request;

    private final OrganizationService service = ServiceFactory
            .getOrganizationService();

    @POST
    public Response post(OrganizationRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        OrganizationResponse organization = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(organization
                .getId().toString()).build();
        return Response.created(location).entity(organization).build();
    }
//    @GET
//    public ListResponse<PatientResponse> getAll(@BeanParam PatientBean bean) {
//        if (bean.getSize() == null)
//            bean.setSize(20);
//        service.configurate(Collections.list(request.getLocales()));
//        return service.getAll(bean.getStart(), bean.getSize(), bean.getSearch(),
//                bean.getOrderBy(), bean.getOrderType(), bean.getGender());
//    }

    @Path("{orgId}/patients")
    public PatientResource patients() {
        return new PatientResource(request);
    }

}
