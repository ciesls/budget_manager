package pl.cieslas.budgetmanager.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Repository
@Transactional
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
