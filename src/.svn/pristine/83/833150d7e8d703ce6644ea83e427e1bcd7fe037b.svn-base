/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.showtracker.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Set session properties.
 *
 * @author Tomáš Svoboda
 */
public class SessionListener implements HttpSessionListener {
 
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(3*60*60);
    }    

    @Override
    public void sessionDestroyed(HttpSessionEvent hse)
    {   
    }
}
