package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.Gender;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PatientData {

    private Long id;

    private String identification;

    private String name;

    private String lastName;

    private Gender gender;

    private Calendar birthday;

    private List<AddressData> addresses;

    private List<EmailData> emails;

    private List<PhoneData> phones;

    public PatientData() {
    }

    public PatientData(String identification, String name, String lastName,
            Gender gender, Calendar birthday) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
    }

    public PatientData(Long id, String identification, String name,
            String lastName, Gender gender, Calendar birthday) {
        this.id = id;
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public List<AddressData> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressData> addresses) {
        this.addresses = addresses;
    }

    public List<EmailData> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailData> emails) {
        this.emails = emails;
    }

    public List<PhoneData> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneData> phones) {
        this.phones = phones;
    }
}
