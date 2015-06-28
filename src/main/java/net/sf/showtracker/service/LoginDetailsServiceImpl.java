/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.List;
import java.util.concurrent.Future;
import net.sf.showtracker.dao.LoginDetailsDao;
import net.sf.showtracker.domain.LoginDetails;
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
@Transactional
public class LoginDetailsServiceImpl implements LoginDetailsService
{
    @Autowired
    private LoginDetailsDao loginDetailsDao;

    @Override
    @Async
    public void create(LoginDetails details)
    {
        loginDetailsDao.create(details);
    }

    @Override
    @Async
    public Future<List<LoginDetails>> getAllByAccount(UserAccount account)
    {
        return new AsyncResult<>(loginDetailsDao.getAllByAccount(account));
    }
}
