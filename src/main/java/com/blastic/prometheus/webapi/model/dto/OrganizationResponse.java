package com.blastic.prometheus.webapi.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class OrganizationResponse extends OrganizationData {

    private Long id;

    private List<AddressResponse> addresses;

    public OrganizationResponse() {
        this.addresses = new ArrayList<>();
    }

    public OrganizationResponse(Long id, String nit, String name,
            String observations) {
        super(nit, name, observations);
        this.id = id;
        this.addresses = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }
}
