package com.blastic.prometheus.webapi.database.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PARTICULAR")
@PrimaryKeyJoinColumn(referencedColumnName = "PERSON_ID")
@NamedQueries({
    @NamedQuery(name = "particular.findByCustomerId",
            query = "SELECT p FROM Particular p "
            + "WHERE p.customerData.id = :customerId")
})
public class Particular extends Person {

    public Particular() {
    }
}
