package com.blastic.prometheus.webapi.database;

import com.blastic.prometheus.webapi.database.dao.AddressDao;
import com.blastic.prometheus.webapi.database.dao.AddressDaoController;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.EmailDaoController;
import com.blastic.prometheus.webapi.database.dao.EstablishmentDao;
import com.blastic.prometheus.webapi.database.dao.EstablishmentDaoController;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDaoController;
import com.blastic.prometheus.webapi.database.dao.ParticularDao;
import com.blastic.prometheus.webapi.database.dao.ParticularDaoController;
import com.blastic.prometheus.webapi.database.dao.PersonDao;
import com.blastic.prometheus.webapi.database.dao.PersonDaoController;
import com.blastic.prometheus.webapi.database.dao.PhoneDao;
import com.blastic.prometheus.webapi.database.dao.PhoneDaoController;

/**
 * The <strong>EntityControllerFactory</strong> class provided methods to get
 * entities controllers.
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0-SNAPSHOT
 */
public class EntityControllerFactory {

    public static ParticularDao getParticularController() {
        return ParticularDaoController.getInstance();
    }

    public static EmailDao getEmailController() {
        return EmailDaoController.getInstance();
    }

    public static AddressDao getAddressController() {
        return AddressDaoController.getInstance();
    }

    public static PhoneDao getPhoneController() {
        return PhoneDaoController.getInstance();
    }

    public static NeighborhoodDao getNeighborhoodController() {
        return NeighborhoodDaoController.getInstance();
    }

    public static EstablishmentDao getEstablishmentDao() {
        return EstablishmentDaoController.getInstance();
    }

    public static PersonDao getPersonController() {
        return PersonDaoController.getInstance();
    }
}
