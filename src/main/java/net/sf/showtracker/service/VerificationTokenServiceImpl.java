/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;
import net.sf.showtracker.dao.VerificationTokenDao;
import net.sf.showtracker.domain.VerificationToken;
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
public class VerificationTokenServiceImpl implements VerificationTokenService
{    
    @Autowired
    private VerificationTokenDao tokenDao;

    @Override
    @Async
    public Future<Void> create(VerificationToken token)
    {
        tokenDao.create(token);
        return new AsyncResult<>(null);
    }

    @Override
    @Async
    public Future<VerificationToken> getByTokenValue(String token)
    {
        return new AsyncResult<>(tokenDao.getByTokenValue(token));
    }

    @Override
    @Async
    public Future<Void> delete(VerificationToken token)
    {
        tokenDao.delete(token);
        return new AsyncResult<>(null);
    }
}
