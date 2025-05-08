package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.ExpenseDto;
import com.momchilgenov.dbcore.entity.Expense;
import com.momchilgenov.dbcore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ExpenseDto> findAllByUserId(Long userId) {
        TypedQuery<Expense> query = entityManager
                .createQuery("FROM Expense e WHERE e.expenseCreator.id=:userId", Expense.class);
        query.setParameter("userId", userId);
        //if we do not use the dto, but instead the entity class, when Jackson tries to
        //serialize the list of expenses, it serializes the first expense and then tries
        //to serialize the user field which has an expense field and thus an endless recursion
        //due to a circular dependency, since they are related db entities, hence the need for dto conversion
        //if we do not, we will essentially receive a single incomplete result of an expense
        return query.getResultList().stream().map(ExpenseDto::new).collect(Collectors.toList());
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
        return entityManager.merge(expense);
    }

    @Override
    @Transactional
    public void delete(Long expenseId) {
        Expense expense = entityManager.find(Expense.class, expenseId);
        entityManager.remove(expense);
    }
}
