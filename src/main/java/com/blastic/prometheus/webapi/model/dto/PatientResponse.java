package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.AcademicLevel;
import com.blastic.prometheus.webapi.database.entity.CivilStatus;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.MilitaryStatus;
import java.util.Calendar;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PatientResponse extends PatientData {

    private Long id;

    private List<EmailResponse> emails;

    private List<PhoneResponse> phones;

    private List<AddressResponse> addresses;

    public PatientResponse() {
    }

    public PatientResponse(Long id, List<EmailResponse> emails,
            List<PhoneResponse> phones, List<AddressResponse> addresses,
            String identification, String name, String lastName, Gender gender,
            Calendar birthday, String observations, String nationality,
            CivilStatus civilStatus, AcademicLevel academicLevel,
            MilitaryStatus militaryStatus, String occupation) {
        super(identification, name, lastName, gender, birthday, observations,
                nationality, civilStatus, academicLevel, militaryStatus,
                occupation);
        this.id = id;
        this.emails = emails;
        this.phones = phones;
        this.addresses = addresses;
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
