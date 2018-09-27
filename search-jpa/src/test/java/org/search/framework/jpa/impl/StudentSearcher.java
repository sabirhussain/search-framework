package org.search.framework.jpa.impl;

import org.search.framework.jpa.JpaSearcher;
import org.search.framework.jpa.entity.Student;

import javax.persistence.EntityManager;

public class StudentSearcher extends JpaSearcher<Student> {

    public StudentSearcher(EntityManager entityManager) {
        super(entityManager);
    }

}
