/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.concurrent.Future;
import net.sf.showtracker.domain.UserAccountAuth;

/**
 * CRUD operations for user account authentication entity.
 *
 * @author Tomáš Svoboda
 */
public interface UserAccountAuthService
{
    Future<Void> create(UserAccountAuth user);

    Future<Void> delete(UserAccountAuth user);

    Future<UserAccountAuth> getById(Long id);

    Future<UserAccountAuth> getByEmail(String email);

    Future<Void> update(UserAccountAuth user);
}
