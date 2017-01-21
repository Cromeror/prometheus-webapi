package com.blastic.prometheus.webapi.database.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PARTICULAR")
@PrimaryKeyJoinColumn(referencedColumnName = "PERSON_ID")
public class Patient extends Person {

    public Patient() {
    }
}
