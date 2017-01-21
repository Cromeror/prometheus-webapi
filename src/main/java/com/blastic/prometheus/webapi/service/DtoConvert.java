package com.blastic.prometheus.webapi.service;

import java.io.Serializable;

/**
 * The <strong>DtoConverter</strong> interface provided methods for convert
 * entities to DTO(Data Transfer Object) and DTO to entities.
 *
 * @param <E> Entity type
 * @param <D> Data Transfer Object type
 *
 * @author Crist�bal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface DtoConvert<E extends Serializable, D> {

    /**
     * Convert Data Transfer Object to entity
     *
     * @param data Data Transfer Object provided
     * @return entity object
     */
    public E convertToEntity(D data);

    /**
     * Convert entity to Data Transfer Object
     *
     * @param entity Entity provided
     * @return Data Transfer Object
     */
    public D convertToDto(E entity);
}
