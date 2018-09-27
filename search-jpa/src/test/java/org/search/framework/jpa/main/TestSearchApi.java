package org.search.framework.jpa.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.search.framework.core.Query;
import org.search.framework.core.dto.SearchCriterion;
import org.search.framework.core.mapper.SearchCriterionMapper;
import org.search.framework.core.type.DataType;
import org.search.framework.core.util.JsonConverter;
import org.search.framework.core.vo.Criterion;
import org.search.framework.jpa.entity.Student;
import org.search.framework.jpa.impl.StudentQueryBuilder;
import org.search.framework.jpa.impl.StudentSearcher;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class TestSearchApi {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("test");
        entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        Student student = new Student();
        student.setFirstName("Sabir").setLastName("Hussain").setGrade(4).setSection("A");
        entityManager.persist(student);
        System.out.println("Student data created with id - " + student.getStudentId());
        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldSearchData() {
        System.out.println("Staring test....");

        Criterion criterion = findByFirstNameCriterion();
        SearchCriterionMapper mapper = new SearchCriterionMapper() {
        };

        SearchCriterion searchCriterion = mapper.toSearchCriterion(criterion);
        searchCriterion.getFilters().forEach(f -> f.setDataType(DataType.VARCHAR));

        StudentQueryBuilder queryBuilder = new StudentQueryBuilder(entityManager);
        StudentSearcher searcher = new StudentSearcher(entityManager);

        System.out.println("Calling search api....");
        entityManager.getTransaction().begin();
        Query<CriteriaQuery<Student>> query = queryBuilder.build(Student.class, searchCriterion);
        List<Student> students = searcher.search(query);
        students.forEach(System.out::println);
        Assert.assertTrue(students.size() == 1);
        Assert.assertEquals(students.get(0).getLastName(), "Hussain");
        entityManager.getTransaction().commit();
    }

    private Criterion findByFirstNameCriterion() {
        String query = "{\"entity\":\"Student\",\"groupCondition\":\"AND\",\"filters\":[{\"name\":\"firstName\",\"value\":\"Sabir\",\"condition\":\"EQUAL\"}],\"criteria\":[]}";
        return JsonConverter.INSTANCE.getObject(query, Criterion.class).get();
    }
}
