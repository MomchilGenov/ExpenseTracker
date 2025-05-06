package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.entity.Expense;
import com.momchilgenov.dbcore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    private final EntityManager entityManager;
    private final UserDao userDao;

    @Autowired
    public ExpenseDaoImpl(EntityManager entityManager, UserDao userDao) {
        this.entityManager = entityManager;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Expense findById(Long id) {
        return this.entityManager.find(Expense.class, id);
    }


    @Override
    @Transactional
    public Expense findById(Long expenseId, Long userId) {
        TypedQuery<Expense> query = entityManager
                .createQuery("FROM Expense e WHERE e.id=:expenseId AND e.expenseCreator.id=:userId", Expense.class);
        query.setParameter("expenseId", expenseId);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public List<Expense> findAll() {
        TypedQuery<Expense> query = entityManager.createQuery("FROM Expense", Expense.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Expense> findAllByUserId(Long userId) {
        TypedQuery<Expense> query = entityManager
                .createQuery("FROM Expense e WHERE e.expenseCreator.id=:userId", Expense.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Expense expense) {
        this.entityManager.persist(expense);
    }

    @Override
    @Transactional
    public void saveForUser(Expense expense, Long userId) {
        User user = userDao.findById(userId);
        expense.setExpenseCreator(user);
        entityManager.merge(expense);
    }

    @Override
    @Transactional
    public Expense update(Expense expense) {
        return this.entityManager.merge(expense);
    }

    @Override
    @Transactional
    public Expense updateForUser(Expense expense, Long userId) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long expenseId) {

    }
}
