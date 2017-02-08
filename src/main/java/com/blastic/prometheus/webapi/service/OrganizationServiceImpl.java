package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import com.blastic.prometheus.webapi.database.entity.Organization;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.model.OrderType;
import com.blastic.prometheus.webapi.model.dto.AddressRequest;
import com.blastic.prometheus.webapi.model.dto.AddressResponse;
import com.blastic.prometheus.webapi.model.dto.ErrorMessageData;
import com.blastic.prometheus.webapi.model.dto.OrganizationRequest;
import com.blastic.prometheus.webapi.model.dto.OrganizationResponse;
import com.blastic.prometheus.webapi.model.dto.ListResponse;
import com.blastic.prometheus.webapi.model.dto.NeighborhoodResponse;
import com.blastic.prometheus.webapi.service.exception.ServiceException;
import com.blastic.prometheus.webapi.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.blastic.prometheus.webapi.database.dao.OrganizationDao;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class OrganizationServiceImpl extends GenericService implements OrganizationService,
        DtoConverter<Organization, OrganizationRequest, OrganizationResponse> {

    private final OrganizationDao organizationDao = EntityControllerFactory
            .getOrganizationDao();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public OrganizationResponse add(OrganizationRequest data) {
        if (data == null) {
            throw new BadRequestException(config
                    .getString("organization.is_null"));
        } else {
            if (TextUtil.isEmpty(data.getNit())) {
                throw new BadRequestException(
                        config.getString("organization.nit"));
            } else if (organizationDao.findByNit(data.getNit()) != null) {
                String msg = String.format(
                        config.getString("organization.nit_used"), data.getNit());
                throw new BadRequestException(msg);
            }

            ErrorMessageData errors = new ErrorMessageData();
            if (data.getName() == null)
                errors.addMessage(config
                        .getString("organization.name_required"));

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

        return convertToDto(organizationDao.save(convertToEntity(data)));
    }

    @Override
    public OrganizationResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns the organization entity by id provided
     *
     * @param id Organization id
     * @return Searched entity
     */
    public Organization getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config
                    .getString("organization.id_required"));

        Organization organization = organizationDao.find(id);

        if (organization == null)
            throw new NotFoundException(String.format(config
                    .getString("organization.not_found"), id));

        return organization;
    }

    @Override
    public ListResponse<OrganizationResponse> getAll(int start, int size,
            String search, String orderBy, OrderType orderType) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<OrganizationResponse> response = new ArrayList<>();
        List<Organization> result = organizationDao.findAll(start, size,
                search, orderBy, orderType);
        for (Organization establishment : result) {
            response.add(convertToDto(establishment));
        }

        return new ListResponse<>(organizationDao.findAllCount(search), response);
    }

    @Override
    public OrganizationResponse update(Long id,
            OrganizationRequest data) {

        if (data == null)
            throw new BadRequestException(config
                    .getString("organization.is_null"));

        Organization entity = getEntity(id);

        if (!TextUtil.isEmpty(data.getNit()) && !data.getNit()
                .equalsIgnoreCase(entity.getNit()))
            entity.setNit(data.getNit());
        if (!TextUtil.isEmpty(data.getName()) && !data.getName()
                .equalsIgnoreCase(entity.getName()))
            entity.setName(data.getName());

        return convertToDto(organizationDao.save(entity));
    }

    @Override
    public OrganizationResponse delete(Long id) {
        OrganizationResponse data = get(id);
        organizationDao.delete(id);
        return data;
    }

    @Override
    public Organization convertToEntity(OrganizationRequest data) {
        Organization establishment = new Organization(data.getNit(),
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
    public OrganizationResponse convertToDto(Organization entity) {
        OrganizationResponse data
                = new OrganizationResponse(entity.getId(),
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
