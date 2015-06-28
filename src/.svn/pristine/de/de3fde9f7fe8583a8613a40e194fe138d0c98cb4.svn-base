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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents login details entity.
 *
 * @author Tomáš Svoboda
 */
@Entity
@Table(name = "login_details")
public class LoginDetails implements Serializable
{
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private Long accountId;

    @Id
    @Column(name = "login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "LoginDetails{" + "accountId=" + accountId + ", calendar=" + time;// + ", account=" + account + '}';
    }
}
