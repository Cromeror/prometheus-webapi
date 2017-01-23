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
public class PatientRequest extends PatientData {

    private List<AddressRequest> addresses;

    private List<EmailRequest> emails;

    private List<PhoneRequest> phones;

    public PatientRequest() {
    }

    public PatientRequest(List<AddressRequest> addresses,
            List<EmailRequest> emails, List<PhoneRequest> phones,
            String identification, String name, String lastName,
            Gender gender, Calendar birthday, String observations,
            String nationality, CivilStatus civilStatus,
            AcademicLevel academicLevel, MilitaryStatus militaryStatus,
            String occupation) {
        super(identification, name, lastName, gender, birthday, observations,
                nationality, civilStatus, academicLevel, militaryStatus,
                occupation);
        this.addresses = addresses;
        this.emails = emails;
        this.phones = phones;
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }

    public List<EmailRequest> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailRequest> emails) {
        this.emails = emails;
    }

    public List<PhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequest> phones) {
        this.phones = phones;
    }
}
