/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.concurrent.Future;
import net.sf.showtracker.domain.UserAccount;

/**
 * Get simplified user account entity without authentication details.
 *
 * @author Tomáš Svoboda
 */
public interface UserAccountService
{
    Future<UserAccount> getByEmail(String username);
}
