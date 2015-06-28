/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents authenticated user account entity.
 *
 * @author Tomáš Svoboda
 */
@Entity
@Table(name = "users_auth")
public class UserAccountAuth implements UserDetails, CredentialsContainer, Serializable
{
    public static final String USER_ROLE = "USER_ROLE";
    private static final boolean IS_ACCOUNT_LOCKED = false;
    private static final boolean IS_CREDENTIALS_EXPIRED = false;
    private static final boolean IS_ACCOUNT_EXPIRED = false;

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "userAccount"))
    private Long userAccountId;

    @Size(max = 80)
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private UserAccount userAccount;

    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public UserAccountAuth()
    {
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public Long getUserAccountId()
    {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId)
    {
        this.userAccountId = userAccountId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(USER_ROLE));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return !IS_ACCOUNT_EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !IS_ACCOUNT_LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return !IS_CREDENTIALS_EXPIRED;
    }

    @Override
    public String getUsername()
    {
        return userAccount.getEmail();
    }

    @Override
    public String toString()
    {
        return "UserAccountAuth{" + "userAccountId=" + userAccountId + ", password=" + password + ", enabled=" + enabled + ", userAccount=" + userAccount + '}';
    }

    @Override
    public void eraseCredentials()
    {
        password = null;
    }
}
