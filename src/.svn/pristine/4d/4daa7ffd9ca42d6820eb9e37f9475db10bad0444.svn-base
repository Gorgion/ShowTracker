/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import net.sf.showtracker.domain.UserAccount;
import net.sf.showtracker.exception.UserNotFoundException;
import org.hibernate.Criteria;
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
public class UserAccountDaoImpl implements UserAccountDao
{
//    final static Logger log = LoggerFactory.getLogger(UserAccountDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public UserAccount getByEmail(String email) throws UserNotFoundException
    {
        Criteria query = getCurrentSession().createCriteria(UserAccount.class).add(Restrictions.eq("email", email));

        Object account = query.uniqueResult();

        if (account == null)
        {
//            log.error("No user account found for email '{}'.", email);
            throw new UserNotFoundException("No user account found for email '" + email + "'.");
        }

        return (UserAccount) account;
    }
}
