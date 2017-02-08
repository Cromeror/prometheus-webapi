package com.blastic.prometheus.webapi.model.dto;

import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class OrganizationRequest extends OrganizationData{
    
    private List<AddressRequest> addresses;

    public OrganizationRequest() {
    }

    public OrganizationRequest(String nit, String name, String observations) {
        super(nit, name, observations);
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }
}
