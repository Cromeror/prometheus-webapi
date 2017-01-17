package com.blastic.prometheus.webapi.service;

/**
 * The <strong>ServiceFactory</strong> class provided methods to get services
 * implementations.
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0-SNAPSHOT
 */
public class ServiceFactory {

    public static PatientService getPatientService() {
        return new PatientServiceImpl();
    }
}
