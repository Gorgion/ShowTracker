/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import net.sf.showtracker.domain.UserAccount;
import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.exception.ServiceException;
import net.sf.showtracker.exception.UserAlreadyExistsException;
import net.sf.showtracker.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomáš Svoboda
 */
@Repository
@Transactional
public class UserAccountAuthDaoImpl implements UserAccountAuthDao
{

//    final static Logger log = LoggerFactory.getLogger(UserAccountAuthDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(UserAccountAuth user) throws UserAlreadyExistsException
    {
        if (user == null)
        {
//            log.error("User account is null.");
            throw new IllegalArgumentException("User account is null.");
        }

        try
        {
            try
            {
                getByEmail(user.getUserAccount().getEmail());

//                log.error("Error during creating new account.");
                throw new UserAlreadyExistsException("Error during creating new account - username already taken.");
            } catch (UserNotFoundException e)
            {

                getCurrentSession().save(user);
            }
        } catch (HibernateException e)
        {

//            log.error("Error during creating new account.", e);
            throw new ServiceException("Error during creating new account occured.", e);
        }
    }

    @Override
    public void update(UserAccountAuth user) throws UserNotFoundException
    {
        try
        {
            UserAccountAuth userAuthToUpdate = getById(user.getUserAccountId());
            UserAccount userToUpdate = userAuthToUpdate.getUserAccount();

            userToUpdate.setName(user.getUserAccount().getName());
            userToUpdate.setEmail(user.getUserAccount().getEmail());

            userAuthToUpdate.setPassword(user.getPassword());
            userAuthToUpdate.setUserAccount(userToUpdate);

            getCurrentSession().update(userAuthToUpdate);
        } catch (HibernateException e)
        {
//            log.error("Error during updating user account.", e);
            throw new ServiceException("Error during updating user account occured.", e);
        }
    }

    @Override
    public UserAccountAuth getById(Long id) throws UserNotFoundException
    {
        Object account;
        try
        {
            account = getCurrentSession().get(UserAccountAuth.class, id);
        } catch (HibernateException e)
        {
//            log.error("Error during retrieving user account.", e);
            throw new ServiceException("Error during retrieving user account occured.", e);
        }

        if (account == null)
        {
//            log.error("No user account found for id [{}].", id);
            throw new UserNotFoundException("No user account found for id [" + id + "].");
        }

        return (UserAccountAuth) account;
    }

    @Override
    public UserAccountAuth getByEmail(String email) throws UserNotFoundException
    {
        try
        {
            String hql = "FROM UserAccountAuth AS u WHERE u.userAccount.email = :email";
            Query query = getCurrentSession().createQuery(hql);
            query.setParameter("email", email);

            Object account = query.uniqueResult();

            if (account == null)
            {
//            log.error("No user account found for username '{}'.", name);
                throw new UserNotFoundException("No user account found for email '" + email + "'.");
            }

            return (UserAccountAuth) account;
        } catch (HibernateException e)
        {
//            log.error("Error during updating user account.", e);
            throw new ServiceException("Error during retrieving user account occured.", e);
        }
    }

    @Override
    public void delete(UserAccountAuth user) throws UserNotFoundException
    {
        try
        {
            UserAccountAuth accountToDelete = getById(user.getUserAccountId());

            getCurrentSession().delete(accountToDelete.getUserAccount());
        } catch (HibernateException e)
        {
//            log.error("Error during updating user account.", e);
            throw new ServiceException("Error during deleting user account occured.", e);
        }
    }
}
