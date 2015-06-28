/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import java.util.List;
import net.sf.showtracker.domain.LoginDetails;
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
public class LoginDetailsDaoImpl implements LoginDetailsDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(LoginDetails details)
    {
        getCurrentSession().save(details);
    }

    @Override
    public List<LoginDetails> getAllByAccount(UserAccount account)
    {
        return getCurrentSession().createCriteria(LoginDetails.class).add(Restrictions.eq("accountId", account.getId())).setMaxResults(20).addOrder(Order.desc("time")).list();
    }
}
