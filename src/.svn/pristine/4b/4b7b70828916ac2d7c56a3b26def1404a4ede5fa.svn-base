/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.List;
import java.util.concurrent.Future;
import net.sf.showtracker.domain.Show;
import net.sf.showtracker.domain.UserAccount;

/**
 * Service manipulating with shows - CRUD.
 *
 * @author Tomáš Svoboda
 */
public interface ShowService
{
    Future<Long> create(Show show);

    Future<List<Show>> getAllForUser(UserAccount user);

    Future<Show> getById(long id);

    Future<Void> remove(Show show);

    Future<Void> removeById(long id);

    Future<Void> update(Show show);
}
