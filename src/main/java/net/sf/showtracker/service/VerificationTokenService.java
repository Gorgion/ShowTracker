/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.concurrent.Future;
import net.sf.showtracker.domain.VerificationToken;

/**
 * Service for verification tokens.
 *
 * @author Tomáš Svoboda
 */
public interface VerificationTokenService
{

    Future<Void> create(VerificationToken token);

    Future<Void> delete(VerificationToken token);

    Future<VerificationToken> getByTokenValue(String token);

}
