/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomáš Svoboda
 */
@Service
public class RememberMeServiceImpl implements RememberMeService
{

    /**
     * Check if user is login by remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    @Override
    public boolean isRememberMeAuthenticated()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
        {
            return false;
        }

        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    /**
     * save targetURL in session
     */
    @Override
    public void setRememberMeTargetUrlToSession(HttpServletRequest request, String targetUrl)
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            session.setAttribute("targetUrl", targetUrl);
        }
    }

    /**
     * get targetURL from session
     */
    @Override
    public String getRememberMeTargetUrlFromSession(HttpServletRequest request)
    {
        String targetUrl = "/";
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            targetUrl = session.getAttribute("targetUrl") == null ? "/" : session.getAttribute("targetUrl").toString();
        }

        return targetUrl;
    }
}
