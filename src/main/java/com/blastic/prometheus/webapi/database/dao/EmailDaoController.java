package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Email;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailDaoController extends EntityDao<Email, Long>
        implements EmailDao {

    private static final EmailDaoController INSTANCE = new EmailDaoController();

    private EmailDaoController() {
        super(Email.class);
    }

    public static EmailDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Email> getAllByPersonId(Long personId) {
        return executeNamedQueryForList("email.findAllByPersonId",
                new Parameter("personId", personId));
    }

    @Override
    public boolean exists(String address) {
        return executeNamedQuery("email.findByAddress",
                new Parameter("address", address)) != null;
    }

}
