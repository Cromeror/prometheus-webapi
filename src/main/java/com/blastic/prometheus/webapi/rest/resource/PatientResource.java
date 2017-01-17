package com.blastic.prometheus.webapi.rest.resource;

import com.blastic.prometheus.webapi.model.dto.PatientData;
import com.blastic.prometheus.webapi.service.PatientService;
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

//    @GET
//    public List<PatientData> getAll(@BeanParam ParticularBean bean) {
//        if (bean.getSize() == null)
//            bean.setSize(20);
//        service.configurate(Collections.list(request.getLocales()));
//        return service.getAll(bean.getStart(), bean.getSize(), bean.getSearch(),
//                bean.getOrderBy(), bean.getOrderType(), bean.getGender());
//    }
//
//    @GET
//    @Path("{id}")
//    public PatientData get(@PathParam("id") Long id) {
//        service.configurate(Collections.list(request.getLocales()));
//        return service.get(id);
//    }
    @POST
    public Response post(PatientData data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        PatientData newParticular = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(newParticular
                .getId().toString()).build();
        return Response.created(location).entity(newParticular).build();
    }
//
//    @PUT
//    @Path("{id}")
//    public PatientData put(@PathParam("id") Long id,
//            PatientData data) {
//        service.configurate(Collections.list(request.getLocales()));
//        return service.update(id, data);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public PatientData delete(@PathParam("id") Long id) {
//        service.configurate(Collections.list(request.getLocales()));
//        return service.delete(id);
//    }
////
////    @Path("{ownerId}/addresses")
////    public AddressResource addresses() {
////        return new AddressResource(request);
////    }
////
////    @Path("{ownerId}/emails")
////    public EmailResource emails() {
////        return new EmailResource(request);
////    }
////
////    @Path("{ownerId}/phones")
////    public PhoneResource phones() {
////        return new PhoneResource(request);
////    }
////
////    @Path("{id}/params")
////    public ParticularParamResource params() {
////        return new ParticularParamResource(request);
////    }
}
