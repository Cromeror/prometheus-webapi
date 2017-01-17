package com.blastic.prometheus.webapi.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "ESTABLISHMENT")
@NamedQueries({
    @NamedQuery(name = "establishment.findByCustomerId",
            query = "SELECT e FROM Establishment e "
            + "WHERE e.customerData.id = :customerId")
})
public class Establishment implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NIT", length = 15, nullable = false, unique = true)
    private String nit;

    @Column(name = "NAME", length = 60, nullable = false)
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Address> addresses;

    public Establishment() {
    }

    public Establishment(String nit, String name) {
        this.nit = nit;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        if (addresses == null)
            addresses = new ArrayList<>();

        if (address != null) {
            this.addresses.add(address);

            if (address.getEstablishment() != this)
                address.setEstablishment(this);
        }
    }
}
