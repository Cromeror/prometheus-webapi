package com.blastic.prometheus.webapi.rest.mapper;

import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Provider
public class ServiceExceptionMapper
        implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        ErrorMessageData errorMessage = e.getErrorMessage();
        return Response.status(Response.Status
                .fromStatusCode(errorMessage.getStatusCode()))
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
