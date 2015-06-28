/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.concurrent.Future;
import net.sf.showtracker.dao.UserAccountDao;
import net.sf.showtracker.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomáš Svoboda
 */
@Service
public class UserAccountServiceImpl implements UserAccountService
{

    @Autowired
    private UserAccountDao userDao;

    @Override
    @Transactional(readOnly = true)
    @Async
    public Future<UserAccount> getByEmail(String email)
    {
        return new AsyncResult<>(userDao.getByEmail(email));
    }
}
