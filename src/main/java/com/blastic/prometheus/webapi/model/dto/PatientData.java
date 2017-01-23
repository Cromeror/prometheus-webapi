package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.AcademicLevel;
import com.blastic.prometheus.webapi.database.entity.CivilStatus;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.MilitaryStatus;
import java.util.Calendar;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PatientData {

    protected String identification;

    protected String name;

    protected String lastName;

    protected Gender gender;

    protected Calendar birthday;

    protected String observations;

    protected String nationality;

    protected CivilStatus civilStatus;

    protected AcademicLevel academicLevel;

    protected MilitaryStatus militaryStatus;

    protected String occupation;

    public PatientData() {
    }

    public PatientData(String identification, String name, String lastName,
            Gender gender, Calendar birthday, String observations,
            String nationality, CivilStatus civilStatus,
            AcademicLevel academicLevel, MilitaryStatus militaryStatus,
            String occupation) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.observations = observations;
        this.nationality = nationality;
        this.civilStatus = civilStatus;
        this.academicLevel = academicLevel;
        this.militaryStatus = militaryStatus;
        this.occupation = occupation;
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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public MilitaryStatus getMilitaryStatus() {
        return militaryStatus;
    }

    public void setMilitaryStatus(MilitaryStatus militaryStatus) {
        this.militaryStatus = militaryStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
