/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import net.sf.showtracker.dao.ShowDao;
import net.sf.showtracker.domain.Show;
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
public class ShowServiceImpl implements ShowService
{

    @Autowired
    private ShowDao showDao;

    @Override
    @Transactional
    @Async
    public Future<Long> create(Show show)
    {
        Long id = showDao.create(show);
        return new AsyncResult<>(id);
    }

    @Override
    @Transactional
    @Async
    public Future<Void> update(Show show)
    {
        showDao.update(show);
        return new AsyncResult<>(null);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public Future<Show> getById(long id)
    {
        return new AsyncResult<>(showDao.getById(id));
    }

    @Override
    @Transactional
    @Async
    public Future<Void> remove(Show show)
    {
        showDao.remove(show);
        return new AsyncResult<>(null);
    }

    @Override
    @Transactional
    @Async
    public Future<Void> removeById(long id)
    {
        Show show = new Show();
        show.setId(id);

        remove(show);
        return new AsyncResult<>(null);

    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public Future<List<Show>> getAllForUser(UserAccount user)
    {
        List<Show> shows = showDao.getAllForUser(user);

        if (shows == null || shows.isEmpty())
        {
            shows = Collections.EMPTY_LIST;
        }

        return new AsyncResult<>(shows);
    }
}
