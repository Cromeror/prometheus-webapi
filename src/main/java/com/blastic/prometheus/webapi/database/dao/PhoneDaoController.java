package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Phone;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class PhoneDaoController extends EntityDao<Phone, Long>
        implements PhoneDao {

    private static final PhoneDaoController INSTANCE = new PhoneDaoController();

    private PhoneDaoController() {
        super(Phone.class);
    }

    public static PhoneDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Phone> getAllByPersonId(Long personId) {
        return executeNamedQueryForList("phone.findAllByPersonId",
                new Parameter("personId", personId));
    }

    @Override
    public boolean exists(String phone) {
        return executeNamedQuery("phone.findByNumber",
                new Parameter("number", phone)) != null;
    }

}
