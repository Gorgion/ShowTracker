/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.concurrent.Future;
import net.sf.showtracker.dao.UserAccountAuthDao;
import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.exception.UserAlreadyExistsException;
import net.sf.showtracker.exception.UserNotFoundException;
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
public class UserAccountAuthServiceImpl implements UserAccountAuthService
{

    @Autowired
    private UserAccountAuthDao userDao;

    @Override
    @Async
    public Future<Void> create(UserAccountAuth user) throws UserAlreadyExistsException
    {
        userDao.create(user);
        return new AsyncResult<>(null);
    }

    @Override
    @Async
    public Future<Void> update(UserAccountAuth user) throws UserNotFoundException
    {
        userDao.update(user);
        return new AsyncResult<>(null);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public Future<UserAccountAuth> getById(Long id) throws UserNotFoundException
    {
        UserAccountAuth account = userDao.getById(id);

        return new AsyncResult<>(account);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public Future<UserAccountAuth> getByEmail(String email) throws UserNotFoundException
    {
        UserAccountAuth account = userDao.getByEmail(email);

        return new AsyncResult<>(account);
    }

    @Override
    @Async
    public Future<Void> delete(UserAccountAuth user) throws UserNotFoundException
    {
        userDao.delete(user);
        return new AsyncResult<>(null);
    }
}
