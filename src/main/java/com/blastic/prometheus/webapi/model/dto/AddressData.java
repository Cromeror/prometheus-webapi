package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class AddressData {

    protected String tag;

    protected String residentialAddress;

    public AddressData() {
    }

    public AddressData(String tag, String residentialAddress) {
        this.tag = tag;
        this.residentialAddress = residentialAddress;
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
}
