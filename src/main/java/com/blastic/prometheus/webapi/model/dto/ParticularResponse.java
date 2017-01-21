package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.Gender;
import java.util.Calendar;
import java.util.List;

/**
 * @author Crist�bal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class ParticularResponse extends PatientData {

    private Long id;

    private List<EmailResponse> emails;

    private List<PhoneResponse> phones;

    private List<AddressResponse> addresses;

    public ParticularResponse() {
    }

    public ParticularResponse(Long id, String identification, String name,
            String lastName, Gender gender, Calendar birthday,
            String observations) {
        super(identification, name, lastName, gender, birthday, observations);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EmailResponse> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailResponse> emails) {
        this.emails = emails;
    }

    public List<PhoneResponse> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneResponse> phones) {
        this.phones = phones;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }
}
