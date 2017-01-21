package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.dao.ParticularDao;
import com.blastic.prometheus.webapi.database.dao.PhoneDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import com.blastic.prometheus.webapi.database.entity.Email;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.database.entity.Particular;
import com.blastic.prometheus.webapi.database.entity.Phone;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.AddressRequest;
import com.blastic.prometheus.webapi.model.dto.AddressResponse;
import com.blastic.prometheus.webapi.model.dto.EmailRequest;
import com.blastic.prometheus.webapi.model.dto.EmailResponse;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.model.dto.ParticularRequest;
import com.blastic.prometheus.webapi.model.dto.ParticularResponse;
import com.blastic.prometheus.webapi.model.dto.PhoneRequest;
import com.blastic.prometheus.webapi.model.dto.PhoneResponse;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ParticularServiceImpl extends GenericService implements ParticularService,
        DtoConverter<Particular, ParticularRequest, ParticularResponse> {

    private final ParticularDao particularDao = EntityControllerFactory
            .getParticularController();

    private final EmailDao emailDao = EntityControllerFactory
            .getEmailController();

    private final PhoneDao phoneDao = EntityControllerFactory
            .getPhoneController();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public ParticularResponse add(ParticularRequest data) {
        if (data == null)
            throw new BadRequestException(config
                    .getString("particular.is_null"));
        else {
            ErrorMessageData errors = new ErrorMessageData();
            if (data.getName() == null)
                errors.addMessage(config.getString("person.name_required"));
            if (data.getLastName() == null)
                errors.addMessage(config.getString("person.last_name_required"));
            if (data.getBirthday() == null)
                errors.addMessage(config.getString("person.birthday_required"));
            if (data.getGender() == null)
                errors.addMessage(config.getString("person.gender_required"));

            if (data.getAddresses() != null && !data.getAddresses().isEmpty()) {
                for (AddressRequest address : data.getAddresses()) {
                    if (address.getNeighborhood() == null)
                        errors.addMessage(config
                                .getString("address.neighborhood_null"));
                    if (TextUtil.isEmpty(address.getResidentialAddress()))
                        errors.addMessage(config
                                .getString("address.resid_address_null"));
                    if (TextUtil.isEmpty(address.getTag()))
                        errors.addMessage(config.getString("address.tag_null"));
                }
            } else
                errors.addMessage(config.getString("addres.at_least"));

            if (data.getEmails() != null && !data.getEmails().isEmpty()) {
                for (EmailRequest edata : data.getEmails()) {
                    if (TextUtil.isEmpty(edata.getAddress()))
                        errors.addMessage(config
                                .getString("email.address_null"));
                    else {
                        if (!TextUtil.isEmail(edata.getAddress()))
                            errors.addMessage(String.format(config
                                    .getString("email.address_valid"),
                                    edata.getAddress()));
                        if (emailDao.exists(edata.getAddress()))
                            errors.addMessage(String.format(config
                                    .getString("email.a_exists"), edata
                                    .getAddress()));
                    }
                }
            }

            if (data.getPhones() != null && !data.getPhones().isEmpty()) {
                for (PhoneRequest pdata : data.getPhones()) {
                    if (TextUtil.isEmpty(pdata.getNumber()))
                        errors.addMessage(config
                                .getString("phone.number_null"));
                    else {
                        if (!TextUtil.isPhone(pdata.getNumber()))
                            errors.addMessage(String.format(config
                                    .getString("phone.is_valid"),
                                    pdata.getNumber()));
                        if (phoneDao.exists(pdata.getNumber()))
                            errors.addMessage(String.format(config
                                    .getString("phone.n_exists"),
                                    pdata.getNumber()));
                    }
                }
            } else
                errors.addMessage(config.getString("phone.at_least"));

            if (!errors.getMessages().isEmpty()) {
                errors.setStatusCode(Response.Status.BAD_REQUEST
                        .getStatusCode());
                throw new ServiceException(errors);
            }
        }

        return convertToDto(particularDao.save(convertToEntity(data)));
    }

    @Override
    public ParticularResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns the particular entity from id provided
     *
     * @param id ID of the particular
     * @return searched entity
     */
    private Particular getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Particular particular = particularDao.findByCustomerId(id);

        if (particular == null)
            throw new NotFoundException(String.format(config
                    .getString("particular.not_found"), id));

        return particular;
    }

    @Override
    public ListResponse<ParticularResponse> getAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<ParticularResponse> response = new ArrayList<>();
        List<Particular> result = particularDao.findAll(start, size, search,
                orderBy, orderType, gender);

        for (Particular particular : result) {
            response.add(convertToDto(particular));
        }

        return new ListResponse<>(particularDao.findAllCount(search, gender),
                response);
    }

    @Override
    public ParticularResponse update(Long id, ParticularRequest data) {
        Particular entity = getEntity(id);

        if (!TextUtil.isEmpty(data.getIdentification()) && !data
                .getIdentification().equalsIgnoreCase(entity
                        .getIdentification()))
            entity.setIdentification(data.getIdentification());
        if (!TextUtil.isEmpty(data.getName()) && !data.getName()
                .equalsIgnoreCase(entity.getName()))
            entity.setName(data.getName());
        if (!TextUtil.isEmpty(data.getLastName()) && !data.getLastName()
                .equalsIgnoreCase(entity.getLastName()))
            entity.setLastName(data.getLastName());
        if (data.getGender() != null && data.getGender() != entity.getGender())
            entity.setGender(data.getGender());
        if (data.getBirthday() != null && !data.getBirthday().equals(entity
                .getBirthday()))
            entity.setBirthday(data.getBirthday());

        return convertToDto(particularDao.save(entity));
    }

    @Override
    public ParticularResponse delete(Long id) {
        ParticularResponse data = get(id);
        particularDao.delete(id);
        return data;
    }

    @Override
    public Particular convertToEntity(ParticularRequest data) {
        Particular particular = new Particular();

        particular.setIdentification(data.getIdentification());
        particular.setName(data.getName());
        particular.setLastName(data.getLastName());
        particular.setBirthday(data.getBirthday());
        particular.setGender(data.getGender());

        for (AddressRequest adata : data.getAddresses()) {
            Neighborhood neighborhood = neighborhoodDao.find(adata
                    .getNeighborhood());
            if (neighborhood == null)
                throw new NotFoundException(String.format(config
                        .getString("address.neighborhood_not_found"), adata
                        .getNeighborhood()));
            particular.addAddress(new Address(adata.getTag(),
                    adata.getResidentialAddress(), neighborhood));
        }

        for (EmailRequest edata : data.getEmails()) {
            particular.addEmail(new Email(edata.getAddress()));
        }

        for (PhoneRequest pdata : data.getPhones()) {
            particular.addPhone(new Phone(pdata.getTag(), pdata.getNumber(),
                    pdata.getPhoneType()));
        }

        return particular;
    }

    @Override
    public ParticularResponse convertToDto(Particular entity) {
        ParticularResponse data = new ParticularResponse();

        data.setId(entity.getId());
        data.setIdentification(entity.getIdentification());
        data.setName(entity.getName());
        data.setLastName(entity.getLastName());
        data.setGender(entity.getGender());
        data.setBirthday(entity.getBirthday());

        if (entity.getAddresses() != null) {
            data.setAddresses(new ArrayList<AddressResponse>());
            for (Address address : entity.getAddresses()) {
                data.getAddresses().add(new AddressResponse(address.getId(),
                        address.getTag(), address.getResidentialAddress(),
                        new NeighborhoodResponse(address.getNeighborhood()
                                .getId(), address.getNeighborhood().getName())));
            }
        }

        if (entity.getEmails() != null) {
            data.setEmails(new ArrayList<EmailResponse>());
            for (Email email : entity.getEmails()) {
                data.getEmails().add(new EmailResponse(email.getAddress(),
                        email.isConfirmed()));
            }
        }

        if (entity.getPhones() != null) {
            data.setPhones(new ArrayList<PhoneResponse>());
            for (Phone phone : entity.getPhones()) {
                data.getPhones().add(new PhoneResponse(phone.getTag(),
                        phone.getNumber(), phone.getType(), phone.isConfirmed()));
            }
        }

        return data;
    }
}
