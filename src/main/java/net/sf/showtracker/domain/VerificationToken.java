/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Token for verification purposes.
 *
 * @author Tomáš Svoboda
 */
@Entity
@Table(name = "verification_tokens")
public class VerificationToken implements Serializable
{
    public static final int DEFAULT_TIME_TO_LIVE = 30;    
        
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;
    
    @Column(name = "expiration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpirationTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public TokenType getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType)
    {
        this.tokenType = tokenType;
    }

    public static enum TokenType
    {
        RESET_PASSWORD
    }
}
