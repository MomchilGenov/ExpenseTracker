package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.entity.Expense;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExpenseDaoImpl implements ExpenseDao {

    private final EntityManager entityManager;

    @Autowired
    public ExpenseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Expense findById(Long id) {
        return this.entityManager.find(Expense.class, id);
    }

    @Override
    public List<Expense> findAll() {
        TypedQuery<Expense> query = entityManager.createQuery("FROM Expense", Expense.class);
        return query.getResultList();
    }

    @Override
    public void save(Expense expense) {
        this.entityManager.persist(expense);
    }

    @Override
    public Expense update(Expense expense) {
        return this.entityManager.merge(expense);
    }
}
