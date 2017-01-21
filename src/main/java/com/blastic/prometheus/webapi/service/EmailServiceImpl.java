package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.PersonDao;
import com.blastic.prometheus.webapi.database.entity.Email;
import com.blastic.prometheus.webapi.database.entity.Person;
import com.blastic.prometheus.webapi.model.dto.EmailRequest;
import com.blastic.prometheus.webapi.model.dto.EmailResponse;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Crist�bal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailServiceImpl extends GenericService implements EmailService,
        DtoConverter<Email, EmailRequest, EmailResponse> {

    private final PersonDao personDao = EntityControllerFactory
            .getPersonController();

    private final EmailDao emailDao = EntityControllerFactory
            .getEmailController();

    @Override
    public EmailResponse add(Long personId, EmailRequest data) {
        if (personId == null || personId <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        if (data == null)
            throw new BadRequestException(config.getString("email.is_null"));

        ErrorMessageData errors = new ErrorMessageData();
        if (data.getAddress() == null)
            errors.addMessage(config.getString("email.address_required"));

        if (!TextUtil.isEmail(data.getAddress()))
            errors.addMessage(String.format(config.getString("email.address_valid"),
                    data.getAddress()));
        else if (emailDao.exists(data.getAddress()))
            errors.addMessage(String.format(config.getString("email.a_exists"),
                    data.getAddress()));

        if (!errors.getMessages().isEmpty()) {
            errors.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(errors);
        }

        Person person = personDao.find(personId);

        if (person == null) {
            throw new NotFoundException(String.format(config
                    .getString("person.not_found"), personId));
        }

        Email email = convertToEntity(data);
        email.setOwner(person);

        email = emailDao.save(email);

        return convertToDto(email);
    }

    @Override
    public EmailResponse get(Integer index, Long personId) {
        return convertToDto(getEmailEntity(personId, index));
    }

    /**
     *
     * @param personId ID of the person
     * @param index email index to search
     * @return searched email
     */
    private Email getEmailEntity(Long personId, Integer index) {

        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("email.index_required"));

        List<Email> emails = emailDao.getAllByPersonId(personId);

        if (index <= emails.size())
            return emails.get(index - 1);
        throw new NotFoundException(String.format(config
                .getString("email.not_found"), personId, index));
    }

    @Override
    public List<EmailResponse> getAllByPersonId(Long personId) {

        if (personId == null || personId <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        if (personDao.find(personId) == null)
            throw new NotFoundException(String.format(config
                    .getString("person.not_found"), personId));

        List<EmailResponse> data = new ArrayList<>();
        for (Email email : emailDao.getAllByPersonId(personId)) {
            data.add(convertToDto(email));
        }

        return data;
    }

    @Override
    public EmailResponse delete(Integer index, Long personId) {
        EmailResponse data = get(index, personId);
        emailDao.delete(getEmailEntity(personId, index).getId());
        return data;
    }

    @Override
    public EmailResponse convertToDto(Email entity) {
        return new EmailResponse(entity.getAddress(),
                entity.isConfirmed());
    }

    @Override
    public Email convertToEntity(EmailRequest data) {
        return new Email(data.getAddress());
    }
}
