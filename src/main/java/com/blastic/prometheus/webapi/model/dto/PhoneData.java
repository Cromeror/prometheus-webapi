package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.PhoneType;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PhoneData {

    private String tag;

    private String number;

    private PhoneType phoneType;

    private Boolean confirmed;

    public PhoneData() {
    }

    public PhoneData(String tag, String number, PhoneType type) {
        this.tag = tag;
        this.number = number;
        this.phoneType = type;
    }

    public PhoneData(String tag, String number, PhoneType type,
            Boolean confirmed) {
        this.tag = tag;
        this.number = number;
        this.phoneType = type;
        this.confirmed = confirmed;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }
}
