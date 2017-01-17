package com.blastic.prometheus.webapi.database.dao;

import com.blastic.prometheus.webapi.database.EntityDao;
import com.blastic.prometheus.webapi.database.entity.Gender;
import com.blastic.prometheus.webapi.database.entity.Patient;
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
public class PatientDaoController extends EntityDao<Patient, Long>
        implements PatientDao {

    private static final Logger LOG = Logger.getLogger(PatientDaoController.class.getName());

    private static final PatientDaoController INSTANCE = new PatientDaoController();

    private PatientDaoController() {
        super(Patient.class);
    }

    public static PatientDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Patient findByIdentification(String identification) {
        return executeNamedQuery("patient.findByIdentification",
                new Parameter("ide", identification));
    }

    @Override
    public List<Patient> findAll(int start, int size, String data,
            String orderBy, OrderType orderType, Gender gender) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Patient> query = cb
                    .createQuery(Patient.class);

            Root<Patient> p = query.from(Patient.class);
            query.select(p);

            Predicate pdata = null;
            if (data != null) {
                Path<String> pathIdentification = p.get("identification");
                Path<String> pathName = p.get("name");
                Path<String> pathLastName = p.get("lastName");

                Predicate p1 = cb.like(pathIdentification, "%" + data + "%");
                Predicate p2 = cb.like(pathName, "%" + data + "%");
                Predicate p3 = cb.like(pathLastName, "%" + data + "%");

                pdata = cb.or(p1, p2, p3);
            }

            if (pdata != null && gender != null)
                query.where(pdata, cb.and(cb.equal(p.get("gender"), gender)));
            else if (gender != null)
                query.where(cb.equal(p.get("gender"), gender));

            if (orderBy != null) {
                Order order;
                if (orderType == null || orderType == OrderType.ASC)
                    order = cb.asc(p.get(orderBy));
                else
                    order = cb.desc(p.get(orderBy));

                query.orderBy(order);
            }

            TypedQuery<Patient> typedQuery = entityManager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setMaxResults(size).setFirstResult(start).getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
    }
}
