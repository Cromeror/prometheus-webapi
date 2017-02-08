package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Organization;
import com.blastic.prometheus.webapi.database.exception.DatabaseException;
import com.blastic.prometheus.webapi.model.OrderType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class OrganizationDaoController extends EntityDao<Organization, Long>
        implements OrganizationDao {

    private static final Logger LOG = Logger
            .getLogger(OrganizationDaoController.class.getName());

    private static final OrganizationDaoController INSTANCE
            = new OrganizationDaoController();

    private OrganizationDaoController() {
        super(Organization.class);
    }

    public static OrganizationDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Organization> findAll(int start, int size, String search,
            String orderBy, OrderType orderType) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Organization> query = cb
                    .createQuery(Organization.class);

            Root<Organization> e = query.from(Organization.class);
            query.select(e);

            Predicate restrictions = getRestrictions(manager, e, search);
            if (restrictions != null)
                query.where(restrictions);

            if (orderBy != null) {
                Order order;
                if (orderType == null || orderType == OrderType.ASC)
                    order = cb.asc(e.get(orderBy));
                else
                    order = cb.desc(e.get(orderBy));

                query.orderBy(order);
            }

            TypedQuery<Organization> typedQuery = manager
                    .createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setMaxResults(size).setFirstResult(start).getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
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

            Root<Organization> e = query.from(Organization.class);
            query.select(cb.count(e));

            Predicate restrictions = getRestrictions(manager, e, search);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager,
            Root<Organization> e, String search) {
        if (search != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<String> pathNit = e.get("nit");
            Path<String> pathName = e.get("name");

            Predicate p1 = cb.like(pathNit, "%" + search + "%");
            Predicate p2 = cb.like(pathName, "%" + search + "%");

            return cb.or(p1, p2);
        }

        return null;
    }

    @Override
    public Organization findByNit(String nit) {
        return executeNamedQuery("organization.findByNit",
                new Parameter("nit", nit));
    }
}
