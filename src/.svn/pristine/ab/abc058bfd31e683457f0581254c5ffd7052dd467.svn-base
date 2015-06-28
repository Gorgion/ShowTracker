/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.List;
import java.util.concurrent.Future;
import net.sf.showtracker.domain.LoginDetails;
import net.sf.showtracker.domain.UserAccount;

/**
 * Service for creating and retrieving login details of user account.
 *
 * @author Tomáš Svoboda
 */
public interface LoginDetailsService
{
    void create(LoginDetails details);

    Future<List<LoginDetails>> getAllByAccount(UserAccount account);
}
