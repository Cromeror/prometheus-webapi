package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.model.dto.PhoneRequest;
import com.blastic.prometheus.webapi.model.dto.PhoneResponse;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PhoneService extends Service {

    /**
     * Create a new phone to a person from the data provided
     *
     * @param personId ID of the person
     * @param data phone information
     * @return saved phone
     */
    PhoneResponse add(Long personId, PhoneRequest data);

    /**
     * Get phone information by id provided
     *
     * @param index phone index to search
     * @param personId ID of the person
     * @return searched phone
     */
    PhoneResponse get(Integer index, Long personId);

    /**
     * Get all phone by person id
     *
     * @param personId ID of the person
     * @return list of phones
     */
    List<PhoneResponse> getAll(Long personId);

    /**
     * Delete phone by id provided
     *
     * @param index phone index to delete
     * @param personId ID of the person
     * @return deleted phone
     */
    PhoneResponse delete(Integer index, Long personId);
}
