package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Person;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PersonDaoController extends EntityDao<Person, Long>
        implements PersonDao {

    private static final PersonDaoController INSTANCE = new PersonDaoController();

    private PersonDaoController() {
        super(Person.class);
    }

    public static PersonDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Person findByIdentification(String identification) {
        return executeNamedQuery("person.findByIdentification",
                new Parameter("ide", identification));
    }

}
