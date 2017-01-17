package com.blastic.prometheus.webapi.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class NeighborhoodData {

    private String name;

    private Long id;

    public NeighborhoodData() {
    }

    public NeighborhoodData(String name) {
        this.name = name;
    }

    public NeighborhoodData(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
