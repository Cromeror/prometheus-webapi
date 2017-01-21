package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.PhoneType;

/**
 * @author Crist�bal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PhoneRequest extends PhoneData {

    public PhoneRequest() {
    }

    public PhoneRequest(String tag, String number, PhoneType type) {
        super(tag, number, type);
    }
}
