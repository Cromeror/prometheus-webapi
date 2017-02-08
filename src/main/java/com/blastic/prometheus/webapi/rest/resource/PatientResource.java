package com.blastic.prometheus.webapi.rest.resource;

import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.PatientRequest;
import com.blastic.prometheus.webapi.model.dto.PatientResponse;
import com.blastic.prometheus.webapi.rest.bean.PatientBean;
import com.blastic.prometheus.webapi.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.blastic.prometheus.webapi.service.PatientService;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("patients")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PatientResource {

    @Context
    private HttpServletRequest request;

    private final PatientService service = ServiceFactory
            .getPatientService();

    public PatientResource() {
    }

    public PatientResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public ListResponse<PatientResponse> getAll(@BeanParam PatientBean bean,
            @PathParam("orgId") Long orgId) {
        if (bean.getSize() == null)
            bean.setSize(20);
        service.configurate(Collections.list(request.getLocales()));

        if (orgId != null)
            return service.getByOrganization(orgId, bean.getStart(),
                    bean.getSize());

        return service.getAll(bean.getStart(), bean.getSize(), bean.getSearch(),
                bean.getOrderBy(), bean.getOrderType(), bean.getGender());
    }

    @GET
    @Path("{id}")
    public PatientResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(PatientRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        PatientResponse newParticular = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(newParticular
                .getId().toString()).build();
        return Response.created(location).entity(newParticular).build();
    }

    @PUT
    @Path("{id}")
    public PatientResponse put(@PathParam("id") Long id,
            PatientRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }

    @DELETE
    @Path("{id}")
    public PatientResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

    @Path("{ownerId}/addresses")
    public AddressResource addresses() {
        return new AddressResource(request);
    }

    @Path("{ownerId}/emails")
    public EmailResource emails() {
        return new EmailResource(request);
    }

    @Path("{ownerId}/phones")
    public PhoneResource phones() {
        return new PhoneResource(request);
    }
}
