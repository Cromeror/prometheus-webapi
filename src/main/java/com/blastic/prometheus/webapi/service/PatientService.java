package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.PatientRequest;
import com.blastic.prometheus.webapi.model.dto.PatientResponse;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PatientService extends Service {

    /**
     * Create new Particular client from the given data
     *
     * @param data Particular entity information
     * @return saved entity
     */
    PatientResponse add(PatientRequest data);

    /**
     * Get Particular client information by id provided
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    PatientResponse get(Long id);

    /**
     * Get all Particular clients
     *
     * @param start initial index
     * @param size size of list
     * @param search String to search
     * @param orderBy Atributte to order
     * @param orderType Order type to list
     * @param gender Filter gender
     * @return Particular entities list
     */
    ListResponse<PatientResponse> getAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender);

    /**
     * Update information of Particular client by id provided
     *
     * @param id entity identifier to update
     * @param data Particular entity information
     * @return saved entity
     */
    PatientResponse update(Long id, PatientRequest data);

    /**
     * Delete Particular client entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    PatientResponse delete(Long id);

}
