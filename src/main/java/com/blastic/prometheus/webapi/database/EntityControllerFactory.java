package com.blastic.prometheus.webapi.database;

import com.blastic.prometheus.webapi.database.dao.AddressDao;
import com.blastic.prometheus.webapi.database.dao.AddressDaoController;
import com.blastic.prometheus.webapi.database.dao.EmailDao;
import com.blastic.prometheus.webapi.database.dao.EmailDaoController;
import com.blastic.prometheus.webapi.database.dao.OrganizationDaoController;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDao;
import com.blastic.prometheus.webapi.database.dao.NeighborhoodDaoController;
import com.blastic.prometheus.webapi.database.dao.PatientDaoController;
import com.blastic.prometheus.webapi.database.dao.PersonDao;
import com.blastic.prometheus.webapi.database.dao.PersonDaoController;
import com.blastic.prometheus.webapi.database.dao.PhoneDao;
import com.blastic.prometheus.webapi.database.dao.PhoneDaoController;
import com.blastic.prometheus.webapi.database.dao.PatientDao;
import com.blastic.prometheus.webapi.database.dao.OrganizationDao;

/**
 * The <strong>EntityControllerFactory</strong> class provided methods to get
 * entities controllers.
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0-SNAPSHOT
 */
public class EntityControllerFactory {

    public static PatientDao getPatientController() {
        return PatientDaoController.getInstance();
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

    public static OrganizationDao getEstablishmentDao() {
        return OrganizationDaoController.getInstance();
    }

    public static PersonDao getPersonController() {
        return PersonDaoController.getInstance();
    }
}
