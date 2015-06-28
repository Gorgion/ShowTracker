/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.security.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.service.UserAccountAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService implementations.
 *
 * @author Tomáš Svoboda
 */
@Service("securityUserDetailsService")
public class SecurityUserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserAccountAuthService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try
        {
            Future<UserAccountAuth> user = userService.getByEmail(username);

            return user.get();
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new UsernameNotFoundException("User not found.", ex);
        }
    }
}
