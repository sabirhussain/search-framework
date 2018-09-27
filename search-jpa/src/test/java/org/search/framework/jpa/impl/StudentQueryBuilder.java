package org.search.framework.jpa.impl;

import org.search.framework.jpa.JpaQueryBuilder;

import javax.persistence.EntityManager;

public class StudentQueryBuilder extends JpaQueryBuilder {

    public StudentQueryBuilder(EntityManager entityManager) {
        super(entityManager);
    }

}
