package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.dao.OrganizationDao;
import com.blastic.prometheus.webapi.database.dao.PhoneDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import com.blastic.prometheus.webapi.database.entity.Email;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.database.entity.Patient;
import com.blastic.prometheus.webapi.database.entity.Phone;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.AddressRequest;
import com.blastic.prometheus.webapi.model.dto.AddressResponse;
import com.blastic.prometheus.webapi.model.dto.EmailRequest;
import com.blastic.prometheus.webapi.model.dto.EmailResponse;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.model.dto.PatientRequest;
import com.blastic.prometheus.webapi.model.dto.PatientResponse;
import com.blastic.prometheus.webapi.model.dto.PhoneRequest;
import com.blastic.prometheus.webapi.model.dto.PhoneResponse;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.blastic.prometheus.webapi.database.dao.PatientDao;
import com.blastic.prometheus.webapi.database.entity.Organization;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PatientServiceImpl extends GenericService implements PatientService,
        DtoConverter<Patient, PatientRequest, PatientResponse> {

    private final PatientDao particularDao = EntityControllerFactory
            .getPatientController();

    private final EmailDao emailDao = EntityControllerFactory
            .getEmailController();

    private final PhoneDao phoneDao = EntityControllerFactory
            .getPhoneController();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    private final OrganizationDao organizationDao
            = EntityControllerFactory.getOrganizationDao();

    /**
     * Returns the organization entity by id provided
     *
     * @param id Organization id
     * @return Searched entity
     */
    public Organization getOrganizationEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config
                    .getString("organization.id_required"));

        Organization organization = organizationDao.findByCustomerId(id);

        if (organization == null)
            throw new NotFoundException(String.format(config
                    .getString("organization.not_found"), id));

        return organization;
    }

    @Override
    public PatientResponse add(PatientRequest data) {
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
    public PatientResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns the particular entity from id provided
     *
     * @param id ID of the particular
     * @return searched entity
     */
    private Patient getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Patient particular = particularDao.findByCustomerId(id);

        if (particular == null)
            throw new NotFoundException(String.format(config
                    .getString("particular.not_found"), id));

        return particular;
    }

    @Override
    public ListResponse<PatientResponse> getAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<PatientResponse> response = new ArrayList<>();
        List<Patient> result = particularDao.findAll(start, size, search,
                orderBy, orderType, gender);

        for (Patient particular : result) {
            response.add(convertToDto(particular));
        }

        return new ListResponse<>(particularDao.findAllCount(search, gender),
                response);
    }

    @Override
    public PatientResponse update(Long id, PatientRequest data) {
        Patient entity = getEntity(id);

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
        if (data.getOrganizationId() != null && data.getOrganizationId() > 0)
            entity.setOrganization(getOrganizationEntity(data.getOrganizationId()));

        return convertToDto(particularDao.save(entity));
    }

    @Override
    public PatientResponse delete(Long id) {
        PatientResponse data = get(id);
        particularDao.delete(id);
        return data;
    }

    @Override
    public Patient convertToEntity(PatientRequest data) {
        Patient patient = new Patient();

        patient.setIdentification(data.getIdentification());
        patient.setName(data.getName());
        patient.setLastName(data.getLastName());
        patient.setBirthday(data.getBirthday());
        patient.setGender(data.getGender());
        patient.setAcademicLevel(data.getAcademicLevel());
        patient.setCivilStatus(data.getCivilStatus());
        patient.setNationality(data.getNationality());
        patient.setOccupation(data.getOccupation());

        for (AddressRequest adata : data.getAddresses()) {
            Neighborhood neighborhood = neighborhoodDao.find(adata
                    .getNeighborhood());
            if (neighborhood == null)
                throw new NotFoundException(String.format(config
                        .getString("address.neighborhood_not_found"), adata
                        .getNeighborhood()));
            patient.addAddress(new Address(adata.getTag(),
                    adata.getResidentialAddress(), neighborhood));
        }
        for (EmailRequest edata : data.getEmails()) {
            patient.addEmail(new Email(edata.getAddress()));
        }

        for (PhoneRequest pdata : data.getPhones()) {
            patient.addPhone(new Phone(pdata.getTag(), pdata.getNumber(),
                    pdata.getPhoneType()));
        }

        return patient;
    }

    @Override
    public PatientResponse convertToDto(Patient entity) {
        PatientResponse data = new PatientResponse();

        data.setId(entity.getId());
        data.setIdentification(entity.getIdentification());
        data.setName(entity.getName());
        data.setLastName(entity.getLastName());
        data.setGender(entity.getGender());
        data.setBirthday(entity.getBirthday());
        data.setAcademicLevel(entity.getAcademicLevel());
        data.setCivilStatus(entity.getCivilStatus());
        data.setNationality(entity.getNationality());
        data.setOccupation(entity.getOccupation());

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
