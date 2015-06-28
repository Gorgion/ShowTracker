/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.showtracker.dao;

import net.sf.showtracker.domain.VerificationToken;
import net.sf.showtracker.exception.ServiceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class VerificationTokenDaoImpl implements VerificationTokenDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void create(VerificationToken token)
    {
        try
        {
            getCurrentSession().save(token);
        } catch(HibernateException e)
        {
            throw new ServiceException("Error during creating new token.", e);
        }
    }
    
    @Override
    public VerificationToken getByTokenValue(String token)
    {
        try
        {
            return (VerificationToken) getCurrentSession().createCriteria(VerificationToken.class).add(Restrictions.idEq(token)).uniqueResult();
        } catch(HibernateException e)
        {
            throw new ServiceException("Error during retrieving token.", e);
        }
    }
    
    @Override
    public void delete(VerificationToken token)
    {
        try
        {
            getCurrentSession().delete(token);
        } catch(HibernateException e)
        {
            throw new ServiceException("Error during deleting token.", e);
        }
    }
}
