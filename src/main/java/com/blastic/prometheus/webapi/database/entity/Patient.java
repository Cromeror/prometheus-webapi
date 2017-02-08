package com.blastic.prometheus.webapi.database.entity;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PATIENT")
@PrimaryKeyJoinColumn(referencedColumnName = "PERSON_ID")
@NamedQueries(
        @NamedQuery(name = "patient.findByOrganizationId",
                query = "SELECT p FROM Patient p WHERE p.organization.id = :orgId")
)
public class Patient extends Person {

    @Column(name = "NATIONALITY", length = 20)
    private String nationality;

    @Column(name = "CIVIL_STATUS")
    private CivilStatus civilStatus;

    @Column(name = "ACADEMIC_LEVEL")
    private AcademicLevel academicLevel;

    @Column(name = "OCCUPATION", length = 20)
    private String occupation;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION")
    private Organization organization;

    public Patient() {
    }

    public Patient(String nationality, CivilStatus civilStatus,
            AcademicLevel academicLevel,
            String occupation, String identification, String name,
            String lastName, Gender gender, Calendar birthday) {
        super(identification, name, lastName, gender, birthday);
        this.nationality = nationality;
        this.civilStatus = civilStatus;
        this.academicLevel = academicLevel;
        this.occupation = occupation;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
