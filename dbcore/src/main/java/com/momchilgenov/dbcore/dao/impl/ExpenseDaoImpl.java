package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.entity.Expense;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    private final EntityManager entityManager;

    @Autowired
    public ExpenseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Expense findById(Long id) {
        return this.entityManager.find(Expense.class, id);
    }

    @Override
    public Expense findById(Long expenseId, Long userId) {
        return null;
    }

    @Override
    @Transactional
    public List<Expense> findAll() {
        TypedQuery<Expense> query = entityManager.createQuery("FROM Expense", Expense.class);
        return query.getResultList();
    }

    @Override
    public List<Expense> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public void save(Expense expense) {
        this.entityManager.persist(expense);
    }

    @Override
    public void saveForUser(Expense expense, Long userId) {

    }

    @Override
    @Transactional
    public Expense update(Expense expense) {
        return this.entityManager.merge(expense);
    }

    @Override
    public Expense updateForUser(Expense expense, Long userId) {
        return null;
    }

    @Override
    public void delete(Long expenseId) {

    }
}
