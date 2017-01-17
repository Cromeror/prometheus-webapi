package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.DataAccessObject;
import com.blastic.prometheus.webapi.database.entity.Person;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PersonDao extends DataAccessObject<Person, Long> {

    /**
     * Get information about a person if existe with the given identification.
     *
     * @param identification identification
     * @return person data
     */
    Person findByIdentification(String identification);
}
