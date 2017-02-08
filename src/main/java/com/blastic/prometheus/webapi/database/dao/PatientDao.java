package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.DataAccessObject;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.Patient;
import com.blastic.prometheus.webapi.model.OrderType;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PatientDao extends DataAccessObject<Patient, Long> {

    /**
     * Return a filter patient list by string
     *
     * @param start Initial index
     * @param size List size
     * @param search String to filter the patient search
     * @param orderBy Atributte to order
     * @param orderType Order type to list
     * @param gender Filter gender
     * @return Filter list
     */
    List<Patient> findAll(int start, int size, String search, String orderBy,
            OrderType orderType, Gender gender);

    /**
     * Returns a patient entity by customer ID
     *
     * @param id Customer ID
     * @param start
     * @param size
     * @return Patient entity
     */
    List<Patient> findByOrganizationId(Long id, int start, int size);

    /**
     * Return the find all result count
     *
     * @param search String to filter the patient search
     * @param gender Filter gender
     * @return Result count
     */
    long findAllCount(String search, Gender gender);
}
