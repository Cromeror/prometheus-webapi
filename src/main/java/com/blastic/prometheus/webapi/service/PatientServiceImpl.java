package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.blastic.prometheus.webapi.database.dao.PatientDao;
import com.blastic.prometheus.webapi.database.dao.PhoneDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import com.blastic.prometheus.webapi.database.entity.Email;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.database.entity.Patient;
import com.blastic.prometheus.webapi.database.entity.Phone;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.AddressData;
import com.blastic.prometheus.webapi.model.dto.EmailData;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodData;
import com.blastic.prometheus.webapi.model.dto.PatientData;
import com.blastic.prometheus.webapi.model.dto.PhoneData;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class PatientServiceImpl extends GenericService implements PatientService,
        DtoConvert<Patient, PatientData> {

    private final PatientDao patientDao = EntityControllerFactory
            .getPatientController();

    private final EmailDao emailDao = EntityControllerFactory
            .getEmailController();

    private final PhoneDao phoneDao = EntityControllerFactory
            .getPhoneController();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public PatientData add(PatientData data) {
        if (data == null)
            throw new BadRequestException(config
                    .getString("particular.is_null"));
        else {
            ErrorMessageData errors = new ErrorMessageData();
            if (data.getIdentification() == null)
                errors.addMessage(config
                        .getString("person.identification_required"));
            if (data.getName() == null)
                errors.addMessage(config.getString("person.name_required"));
            if (data.getLastName() == null)
                errors.addMessage(config.getString("person.last_name_required"));
            if (data.getBirthday() == null)
                errors.addMessage(config.getString("person.birthday_required"));
            if (data.getGender() == null)
                errors.addMessage(config.getString("person.gender_required"));

            if (data.getAddresses() != null && !data.getAddresses().isEmpty()) {
                for (AddressData address : data.getAddresses()) {
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
                for (EmailData edata : data.getEmails()) {
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
                for (PhoneData pdata : data.getPhones()) {
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

        return convertToDto(patientDao.save(convertToEntity(data)));
    }

    @Override
    public PatientData get(Long id) {
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

        Patient patient = patientDao.find(id);

        if (patient == null)
            throw new NotFoundException(String.format(config
                    .getString("particular.not_found"), id));

        return patient;
    }

    @Override
    public List<PatientData> getAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<PatientData> data = new ArrayList<>();
        List<Patient> result = patientDao.findAll(start, size, search,
                orderBy, orderType, gender);

        for (Patient particular : result) {
            data.add(convertToDto(particular));
        }

        return data;
    }

    @Override
    public PatientData update(Long id, PatientData data) {
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

        return convertToDto(patientDao.save(entity));
    }

    @Override
    public PatientData delete(Long id) {
        PatientData data = get(id);
        patientDao.delete(id);
        return data;
    }

    @Override
    public Patient convertToEntity(PatientData data) {
        Patient patient = new Patient();

        patient.setIdentification(data.getIdentification());
        patient.setName(data.getName());
        patient.setLastName(data.getLastName());
        patient.setBirthday(data.getBirthday());
        patient.setGender(data.getGender());

        for (AddressData adata : data.getAddresses()) {
            Neighborhood neighborhood = neighborhoodDao.find(adata
                    .getNeighborhood().getId());
            if (neighborhood == null)
                throw new NotFoundException(String.format(config
                        .getString("address.neighborhood_not_found"), adata
                        .getNeighborhood()));
            patient.addAddress(new Address(adata.getTag(),
                    adata.getResidentialAddress(), neighborhood));
        }

        for (EmailData edata : data.getEmails()) {
            patient.addEmail(new Email(edata.getAddress()));
        }

        for (PhoneData pdata : data.getPhones()) {
            patient.addPhone(new Phone(pdata.getTag(), pdata.getNumber(),
                    pdata.getPhoneType()));
        }

        return patient;
    }

    @Override
    public PatientData convertToDto(Patient entity) {
        PatientData data = new PatientData();

        data.setIdentification(entity.getIdentification());
        data.setName(entity.getName());
        data.setLastName(entity.getLastName());
        data.setGender(entity.getGender());
        data.setBirthday(entity.getBirthday());

        if (entity.getAddresses() != null) {
            data.setAddresses(new ArrayList<AddressData>());
            for (Address address : entity.getAddresses()) {
                data.getAddresses().add(new AddressData(address.getId(),
                        address.getTag(), address.getResidentialAddress(),
                        new NeighborhoodData(address.getNeighborhood().getId(),
                                address.getNeighborhood().getName())));
            }
        }

        if (entity.getEmails() != null) {
            data.setEmails(new ArrayList<EmailData>());
            for (Email email : entity.getEmails()) {
                data.getEmails().add(new EmailData(email.getAddress(),
                        email.isConfirmed()));
            }
        }

        if (entity.getPhones() != null) {
            data.setPhones(new ArrayList<PhoneData>());
            for (Phone phone : entity.getPhones()) {
                data.getPhones().add(new PhoneData(phone.getTag(),
                        phone.getNumber(), phone.getType(), phone.isConfirmed()));
            }
        }

        return data;
    }
}
