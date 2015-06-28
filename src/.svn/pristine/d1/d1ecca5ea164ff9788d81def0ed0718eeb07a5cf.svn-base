/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.dao;

import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.exception.UserAlreadyExistsException;
import net.sf.showtracker.exception.UserNotFoundException;

/**
 *
 * @author Tomáš Svoboda
 */
public interface UserAccountAuthDao
{

    void create(UserAccountAuth user) throws UserAlreadyExistsException;

    void delete(UserAccountAuth user) throws UserNotFoundException;

    UserAccountAuth getById(Long id) throws UserNotFoundException;

    UserAccountAuth getByEmail(String name) throws UserNotFoundException;

    void update(UserAccountAuth user) throws UserNotFoundException;
}
