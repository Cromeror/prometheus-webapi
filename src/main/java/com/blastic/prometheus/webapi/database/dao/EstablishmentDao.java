package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.DataAccessObject;
import com.blastic.prometheus.webapi.database.entity.Establishment;
import com.blastic.prometheus.webapi.model.OrderType;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface EstablishmentDao 
        extends DataAccessObject<Establishment, Long> {
    
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
    List<Establishment> findAll(int start, int size, String search, 
            String orderBy, OrderType orderType);
    
    /**
     * Returns a establishment entity by customer ID
     * 
     * @param id Customer ID
     * @return Establishment entity
     */
    Establishment findByCustomerId(Long id);
    
    /**
     * Returns the find all result count 
     * 
     * @param search String to filter the establishment search
     * @return Result count
     */
    long findAllCount(String search);
}
