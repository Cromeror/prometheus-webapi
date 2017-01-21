package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.PhoneType;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PhoneData {

    protected String tag;

    protected String number;

    protected PhoneType phoneType;

    public PhoneData() {
    }

    public PhoneData(String tag, String number, PhoneType type) {
        this.tag = tag;
        this.number = number;
        this.phoneType = type;
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
