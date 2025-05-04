package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.CategoryDao;
import com.momchilgenov.dbcore.entity.Category;
import jakarta.persistence.EntityManager;
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
    public Category findById(Long categoryId, Long userId) {
        TypedQuery<Category> typedQuery = this.entityManager.
                createQuery("FROM Category c WHERE c.id=:categoryId AND c.userId=:userId", Category.class);
        typedQuery.setParameter("categoryId", categoryId);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getSingleResult();
    }

    @Transactional
    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("FROM Category", Category.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Category> findAllByUserId(Long userId) {
        TypedQuery<Category> typedQuery = this.entityManager.
                createQuery("FROM Category c WHERE c.userId=:userId", Category.class);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }

    @Transactional
    @Override
    public void saveForUser(Category category, Long userId) {
        Category newCategory = new Category();
        newCategory.setUserId(userId);
        newCategory.setName(category.getName());
        this.entityManager.persist(newCategory);
    }

    @Transactional
    @Override
    public Category update(Category category) {
        return this.entityManager.merge(category);
    }

    @Transactional
    @Override
    public Category updateForUser(Category category, Long userId) {
        category.setUserId(userId);
        return this.entityManager.merge(category);
    }

    @Transactional
    @Override
    public void delete(Long categoryId) {
        TypedQuery<Category> query = this.entityManager
                .createQuery("FROM Category c WHERE c.id=:categoryId", Category.class);
        query.setParameter("categoryId", categoryId);
        Category category = query.getSingleResult();
        this.entityManager.remove(category);
    }
}
