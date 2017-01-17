package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.DataAccessObject;
import com.blastic.prometheus.webapi.database.entity.Email;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface EmailDao extends DataAccessObject<Email, Long> {

    /**
     * Returns a list of emails with the person ID provided
     *
     * @param personId Owner id
     * @return List of emails
     */
    List<Email> getAllByPersonId(Long personId);

    /**
     * Check if an email address exists in the database
     *
     * @param address email address
     * @return true if the email address exists in the database, otherwise false
     */
    boolean exists(String address);
}
