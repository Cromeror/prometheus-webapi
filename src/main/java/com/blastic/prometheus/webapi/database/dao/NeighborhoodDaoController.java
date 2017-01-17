package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import java.util.List;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class NeighborhoodDaoController extends EntityDao<Neighborhood, Long> 
        implements NeighborhoodDao {

    private static final NeighborhoodDaoController INSTANCE 
            = new NeighborhoodDaoController();
    
    private NeighborhoodDaoController() {
        super(Neighborhood.class);
    }

    public static NeighborhoodDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Neighborhood> findAll(int start, int size) {
        return executeNamedQueryForList("neighborhood.findAll", start, size);
    }

    @Override
    public Neighborhood findByName(String name) {
        return executeNamedQuery("neighborhood.findByName", 
                new Parameter("name", name));
    }
}
