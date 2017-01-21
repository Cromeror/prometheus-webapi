package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Neighborhood;
import com.blastic.prometheus.webapi.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
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
    public List<Neighborhood> findAll(int start, int size, String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Neighborhood> query = cb.createQuery(Neighborhood.class);
            Root<Neighborhood> neighborhood = query.from(Neighborhood.class);
            query.select(neighborhood);

            Predicate restrictions = getRestrictions(manager, neighborhood, search);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<Neighborhood> typedQuery = manager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setFirstResult(start).setMaxResults(size).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public long findAllCount(String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Neighborhood> neighborhood = query.from(Neighborhood.class);
            query.select(cb.count(neighborhood));

            Predicate restrictions = getRestrictions(manager, neighborhood, search);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager,
            Root<Neighborhood> neighborhood, String search) {

        if (search != null) {
            Path<String> name = neighborhood.get("name");
            return manager.getCriteriaBuilder().like(name, "%" + search + "%");
        }

        return null;
    }

    @Override
    public Neighborhood findByName(String name) {
        return executeNamedQuery("neighborhood.findByName",
                new Parameter("name", name));
    }
}
