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
     * Get information about a person if existe with the given identification.
     *
     * @param identification identification
     * @return person data
     */
    Patient findByIdentification(String identification);

    /**
     * Return a filter particular list by string
     *
     * @param start Initial index
     * @param size List size
     * @param data Filter particular list
     * @param orderBy Atributte to order
     * @param orderType Order type to list
     * @param gender Filter gender
     * @return Filter list
     */
    List<Patient> findAll(int start, int size, String data, String orderBy,
            OrderType orderType, Gender gender);
}
