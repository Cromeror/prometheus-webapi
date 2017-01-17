package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Address;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class AddressDaoController extends EntityDao<Address, Long>
        implements AddressDao {

    private static final AddressDaoController INSTANCE
            = new AddressDaoController();

    private AddressDaoController() {
        super(Address.class);
    }

    public static AddressDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Address> getAllByPersonId(Long personId) {
        return executeNamedQueryForList(
                "address.findAllByPersonId",
                new Parameter("personId", personId));
    }

    @Override
    public List<Address> getAllByEstablishmentId(Long establishmentId) {
        return executeNamedQueryForList(
                "address.findAllByEstablishmentId",
                new Parameter("establishmentId", establishmentId));
    }

    @Override
    public Address getByPersonId(Long id, Long personId) {
        return executeNamedQuery("address.findByPersonId",
                new Parameter("id", id), new Parameter("personId", personId));
    }

    @Override
    public Address getByEstablishmentId(Long id, Long establishmentId) {
        return executeNamedQuery("address.findByEstablishmentId",
                new Parameter("id", id),
                new Parameter("establishmentId", establishmentId));
    }

}
