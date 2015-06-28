/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import java.util.List;
import net.sf.showtracker.domain.Show;
import net.sf.showtracker.domain.UserAccount;

/**
 *
 * @author Tomáš Svoboda
 */
public interface ShowDao
{

    Long create(Show show);

    List<Show> getAllForUser(UserAccount user);

    Show getById(Long id);

    void remove(Show show);

    void update(Show show);

}
