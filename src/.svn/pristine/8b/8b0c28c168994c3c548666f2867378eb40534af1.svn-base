/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.showtracker.service;

import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author Tomáš Svoboda
 */
public interface EmailService
{
    @Async
    Future<String> generatePasswordResetLink(String baseLink);

    @Async
    Future<Void> send(String to, String subject, String text);
    
}
