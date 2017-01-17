package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class AddressData {

    private Long id;

    protected String tag;

    protected String residentialAddress;

    private NeighborhoodData neighborhood;

    public AddressData() {
    }

    public AddressData(String tag, String residentialAddress) {
        this.tag = tag;
        this.residentialAddress = residentialAddress;
    }

    public AddressData(Long id, String tag, String residentialAddress,
            NeighborhoodData neighborhood) {
        this.tag = tag;
        this.residentialAddress = residentialAddress;
        this.id = id;
        this.neighborhood = neighborhood;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public NeighborhoodData getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(NeighborhoodData neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
