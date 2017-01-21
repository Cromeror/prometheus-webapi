package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.EstablishmentDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import com.blastic.prometheus.webapi.database.entity.Establishment;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.AddressRequest;
import com.blastic.prometheus.webapi.model.dto.AddressResponse;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.model.dto.EstablishmentRequest;
import com.blastic.prometheus.webapi.model.dto.EstablishmentResponse;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class EstablishmentServiceImpl extends GenericService implements EstablishmentService,
        DtoConverter<Establishment, EstablishmentRequest, EstablishmentResponse> {

    private final EstablishmentDao establishmentDao = EntityControllerFactory
            .getEstablishmentDao();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public EstablishmentResponse add(EstablishmentRequest data) {
        if (data == null) {
            throw new BadRequestException(config
                    .getString("establishment.is_null"));
        } else {
            ErrorMessageData errors = new ErrorMessageData();
            if (data.getName() == null)
                errors.addMessage(config
                        .getString("establishment.name_required"));

            if (data.getAddresses() != null) {
                for (AddressRequest address : data.getAddresses()) {
                    if (address.getNeighborhood() == null)
                        errors.addMessage(config
                                .getString("address.neighborhood_null"));
                    if (TextUtil.isEmpty(address.getResidentialAddress()))
                        errors.addMessage(config
                                .getString("address.resid_address_nul"));
                    if (TextUtil.isEmpty(address.getTag()))
                        errors.addMessage(config.getString("address.tag_null"));
                }
            } else
                errors.addMessage(config.getString("addres.at_least"));

            if (!errors.getMessages().isEmpty()) {
                errors.setStatusCode(Response.Status.BAD_REQUEST
                        .getStatusCode());
                throw new ServiceException(errors);
            }
        }

        return convertToDto(establishmentDao.save(convertToEntity(data)));
    }

    @Override
    public EstablishmentResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns the establishment entity by id provided
     *
     * @param id Establishment id
     * @return Searched entity
     */
    public Establishment getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config
                    .getString("establishment.id_required"));

        Establishment establishment = establishmentDao.findByCustomerId(id);

        if (establishment == null)
            throw new NotFoundException(String.format(config
                    .getString("establishment.not_found"), id));

        return establishment;
    }

    @Override
    public ListResponse<EstablishmentResponse> getAll(int start, int size,
            String search, String orderBy, OrderType orderType) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<EstablishmentResponse> response = new ArrayList<>();
        List<Establishment> result = establishmentDao.findAll(start, size,
                search, orderBy, orderType);
        for (Establishment establishment : result) {
            response.add(convertToDto(establishment));
        }

        return new ListResponse<>(establishmentDao.findAllCount(search), response);
    }

    @Override
    public EstablishmentResponse update(Long id,
            EstablishmentRequest data) {

        if (data == null)
            throw new BadRequestException(config
                    .getString("establishment.is_null"));

        Establishment entity = getEntity(id);

        if (!TextUtil.isEmpty(data.getNit()) && !data.getNit()
                .equalsIgnoreCase(entity.getNit()))
            entity.setNit(data.getNit());
        if (!TextUtil.isEmpty(data.getName()) && !data.getName()
                .equalsIgnoreCase(entity.getName()))
            entity.setName(data.getName());

        return convertToDto(establishmentDao.save(entity));
    }

    @Override
    public EstablishmentResponse delete(Long id) {
        EstablishmentResponse data = get(id);
        establishmentDao.delete(id);
        return data;
    }

    @Override
    public Establishment convertToEntity(EstablishmentRequest data) {
        Establishment establishment = new Establishment(data.getNit(),
                data.getName());

        for (AddressRequest adata : data.getAddresses()) {
            Neighborhood neighborhood = neighborhoodDao.find(adata
                    .getNeighborhood());
            if (neighborhood == null)
                throw new NotFoundException(String.format(config
                        .getString("address.neighborhood_not_found"), adata
                        .getNeighborhood()));
            establishment.addAddress(new Address(adata.getTag(),
                    adata.getResidentialAddress(), neighborhood));
        }

        return establishment;
    }

    @Override
    public EstablishmentResponse convertToDto(Establishment entity) {
        EstablishmentResponse data
                = new EstablishmentResponse(entity.getId(),
                        entity.getNit(), entity.getName(), null);

        for (Address address : entity.getAddresses()) {
            data.getAddresses().add(new AddressResponse(address.getId(),
                    address.getTag(), address.getResidentialAddress(),
                    new NeighborhoodResponse(address.getNeighborhood().getId(),
                            address.getNeighborhood().getName())));
        }

        return data;
    }
}
