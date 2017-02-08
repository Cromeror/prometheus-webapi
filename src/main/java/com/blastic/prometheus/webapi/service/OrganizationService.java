package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.OrganizationRequest;
import com.blastic.prometheus.webapi.model.dto.OrganizationResponse;
import com.blastic.prometheus.webapi.model.dto.ListResponse;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface OrganizationService extends Service{

    /**
     * Create new Establishment client from the given data
     *
     * @param data information of Establishment entity
     * @return saved entity
     */
    OrganizationResponse add(OrganizationRequest data);

    /**
     * Get Establishment client information by id provided
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    OrganizationResponse get(Long id);

    /**
     * Get all Establishment clients
     *
     * @param start Initial index
     * @param size List size
     * @param search Filter particular list
     * @param orderBy Atributte to order
     * @param orderType Order type to list
     * @return list of Establishment entities
     */
    ListResponse<OrganizationResponse> getAll(int start, int size, String search, 
            String orderBy, OrderType orderType);

    /**
     * Update information of Establishment client by id provided
     *
     * @param id entity identifier to update
     * @param data information of Establishment entity
     * @return saved entity
     */
    OrganizationResponse update(Long id, OrganizationRequest data);

    /**
     * Establishment Particular client entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    OrganizationResponse delete(Long id);
}
