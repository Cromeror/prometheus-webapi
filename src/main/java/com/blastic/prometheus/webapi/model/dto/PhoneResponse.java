package com.blastic.prometheus.webapi.model.dto;

import com.blastic.prometheus.webapi.database.entity.PhoneType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class PhoneResponse extends PhoneData {

    private Boolean confirmed;

    public PhoneResponse() {
    }

    public PhoneResponse(String tag, String number, PhoneType type,
            Boolean confirmed) {
        super(tag, number, type);
        this.confirmed = confirmed;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
