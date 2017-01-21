package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodRequest;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * @author Crist�bal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class NeighborhoodServiceImpl extends GenericService implements NeighborhoodService,
        DtoConverter<Neighborhood, NeighborhoodRequest, NeighborhoodResponse> {

    private final NeighborhoodDao controller;

    public NeighborhoodServiceImpl(NeighborhoodDao neighborhoodDao) {
        this.controller = neighborhoodDao;
    }

    @Override
    public ListResponse<NeighborhoodResponse> getAll(int start, int size, String search) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<NeighborhoodResponse> response = new ArrayList<>();
        for (Neighborhood neighborhood : controller.findAll(start, size, search))
            response.add(convertToDto(neighborhood));

        return new ListResponse<>(controller.findAllCount(search), response);
    }

    /**
     * Return a neighborhood entity by id
     *
     * @param id Neighborhood ID
     * @return Seached entity
     */
    private Neighborhood getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Neighborhood neighborhood = controller.find(id);
        if (neighborhood == null)
            throw new NotFoundException(String.format(config
                    .getString("neighborhood.not_found"), id));

        return neighborhood;
    }

    @Override
    public NeighborhoodResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public NeighborhoodResponse add(NeighborhoodRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("neighborhood.is_null"));
        if (TextUtil.isEmpty(data.getName()))
            throw new BadRequestException(config.getString("neighborhood.name_null"));
        if (controller.findByName(data.getName()) != null)
            throw new BadRequestException(String.format(config
                    .getString("neighborhood.exists"), data.getName()));

        return convertToDto(controller.save(convertToEntity(data)));
    }

    @Override
    public NeighborhoodResponse update(Long id, NeighborhoodRequest data) {
        Neighborhood entity = getEntity(id);

        if (data == null)
            throw new BadRequestException(config.getString("neighborhood.is_null"));
        if (TextUtil.isEmpty(data.getName()))
            throw new BadRequestException(config.getString("neighborhood.name_null"));
        if (!entity.getName().equalsIgnoreCase(data.getName()))
            entity.setName(data.getName());

        return convertToDto(controller.save(entity));
    }

    @Override
    public NeighborhoodResponse delete(Long id) {
        NeighborhoodResponse response = get(id);
        controller.delete(id);
        return response;
    }

    @Override
    public Neighborhood convertToEntity(NeighborhoodRequest data) {
        return new Neighborhood(data.getName());
    }

    @Override
    public NeighborhoodResponse convertToDto(Neighborhood entity) {
        return new NeighborhoodResponse(entity.getId(), entity.getName());
    }
}
