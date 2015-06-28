/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.security;

import java.util.Calendar;
import net.sf.showtracker.domain.LoginDetails;
import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.service.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Stores successful login to DB
 *
 * @author Tomáš Svoboda
 */
@Component
public class AuthenticationApplicationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent>
{
    @Autowired
    private LoginDetailsService loginDetailsService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
        Authentication authentication = event.getAuthentication();

        if (authentication.getPrincipal() instanceof UserAccountAuth)
        {
            LoginDetails ld = new LoginDetails();
            ld.setAccountId(((UserAccountAuth) authentication.getPrincipal()).getUserAccountId());
            ld.setTime(Calendar.getInstance().getTime());

            loginDetailsService.create(ld);
        }
    }
}
