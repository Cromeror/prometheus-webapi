package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodRequest;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface NeighborhoodService extends Service {

    /**
     * Returns neighborhood list paginated
     *
     * @param start Initial index
     * @param size List size
     * @param search String to filter in neighborhood resource
     * @return Neighborhood list
     */
    ListResponse<NeighborhoodResponse> getAll(int start, int size, String search);

    /**
     * Returns a specific neighborhood by ID
     *
     * @param id Neighborhood ID
     * @return Searched entity
     */
    NeighborhoodResponse get(Long id);

    /**
     * Create a new neighborhood
     *
     * @param data Neighborhood information
     * @return Neighborhood created
     */
    NeighborhoodResponse add(NeighborhoodRequest data);

    /**
     * Update a neighborhood
     *
     * @param id Neighborhood ID
     * @param data Neighborhood information
     * @return Updated neighborhood
     */
    NeighborhoodResponse update(Long id, NeighborhoodRequest data);

    /**
     * Delete a neigborhood
     *
     * @param id Neighborhood ID
     * @return Deleted neighborhood
     */
    NeighborhoodResponse delete(Long id);
}
