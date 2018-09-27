package org.search.framework.jpa;

import org.search.framework.core.Query;
import org.search.framework.core.QueryBuilder;
import org.search.framework.core.dto.SearchCriterion;
import org.search.framework.jpa.builder.PredicateBuilder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JpaQueryBuilder implements QueryBuilder {

    private PredicateBuilder predicateBuilder = PredicateBuilder.INSTANCE;

    private EntityManager entityManager;

    public JpaQueryBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> Query<CriteriaQuery<T>> build(Class<T> clazz, SearchCriterion criterion) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        query.distinct(true);
        Root<T> root = query.from(clazz);
        Predicate predicate = predicateBuilder.build(criterion, builder, root);
        query.where(predicate);

        return new Query<CriteriaQuery<T>>() {

            @Override
            public CriteriaQuery<T> get() {
                return query;
            }
        };
    }

    @Override
    public <T> Query<T> build(SearchCriterion criterias) {
        throw new UnsupportedOperationException("Use build(class, criterion) instead.");
    }
}
