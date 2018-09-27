package org.search.framework.jpa;

import org.search.framework.core.Query;
import org.search.framework.core.Searcher;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class JpaSearcher<T> implements Searcher<CriteriaQuery<T>, List<T>> {

    private EntityManager entityManager;

    public JpaSearcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<T> search(Query<CriteriaQuery<T>> query) {
        return entityManager.createQuery(query.get()).getResultList();
    }

}
