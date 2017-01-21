package com.blastic.prometheus.webapi.service;

import com.blastic.prometheus.webapi.database.EntityControllerFactory;

/**
 * The <strong>ServiceFactory</strong> class provided methods to get services
 * implementations.
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0-SNAPSHOT
 */
public class ServiceFactory {

    public static AddressService getAddressService() {
        return new AddressServiceImpl();
    }

    public static EmailService getEmailService() {
        return new EmailServiceImpl();
    }

    public static PhoneService getPhoneService() {
        return new PhoneServiceImpl();
    }

    public static EstablishmentService getEstablishmentService() {
        return new EstablishmentServiceImpl();
    }

    public static PatientService getParticularService() {
        return new PatientServiceImpl();
    }

    public static NeighborhoodService getNeighborhoodService() {
        return new NeighborhoodServiceImpl(EntityControllerFactory
                .getNeighborhoodController());
    }
}
