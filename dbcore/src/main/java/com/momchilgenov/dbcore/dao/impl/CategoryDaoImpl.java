package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.CategoryDao;
import com.momchilgenov.dbcore.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final EntityManager entityManager;

    @Autowired
    public CategoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Category findById(Long id) {
        return this.entityManager.find(Category.class, id);
    }

    @Transactional
    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("FROM Category", Category.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }

    @Override
    public Category update(Category category) {
        return this.entityManager.merge(category);
    }
}
