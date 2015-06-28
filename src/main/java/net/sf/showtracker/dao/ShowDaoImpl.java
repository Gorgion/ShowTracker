/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import java.util.List;
import net.sf.showtracker.domain.Show;
import net.sf.showtracker.domain.UserAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomáš Svoboda
 */
@Repository
@Transactional
public class ShowDaoImpl implements ShowDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Long create(Show show)
    {
        return (Long) getCurrentSession().save(show);
    }

    @Override
    public void update(Show show)
    {
        getCurrentSession().update(show);
    }

    @Override
    public Show getById(Long id)
    {
        return (Show) getCurrentSession().get(Show.class, id);
    }

    @Override
    public void remove(Show show)
    {
        getCurrentSession().delete(show);
    }

    @Override
    public List<Show> getAllForUser(UserAccount user)
    {
        return getCurrentSession().createCriteria(Show.class)
                .add(Restrictions.eq("user", user)).addOrder(Order.asc("name")).list();
    }
}