package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.DataAccessObject;
import com.blastic.prometheus.webapi.database.entity.Organization;
import com.blastic.prometheus.webapi.model.OrderType;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface OrganizationDao
        extends DataAccessObject<Organization, Long> {

    /**
     * Return a filter establishment list by string
     *
     * @param start Initial index
     * @param size List size
     * @param search String to filter the establishment search
     * @param orderBy Atributte to order
     * @param orderType Order type to list
     * @return Filter list
     */
    List<Organization> findAll(int start, int size, String search,
            String orderBy, OrderType orderType);

    /**
     * Gets organization entity by nit provided
     *
     * @param id organization nit
     * @return
     */
    Organization findByNit(String id);

    /**
     * Returns the find all result count
     *
     * @param search String to filter the establishment search
     * @return Result count
     */
    long findAllCount(String search);
}
