/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Represents security configuration.
 *
 * @author Tomáš Svoboda
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    @Qualifier("securityUserDetailsService")
    private UserDetailsService securityUserDetailsService;    

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
   }

    @Bean(name = "authMgr")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/assets/**").permitAll()
            .and()
                .formLogin()
                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .loginPage("/login")
                .failureUrl("/login?error")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")            
            .and()
                .rememberMe()
//                        .useSecureCookie(true)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(7776000)
            .and()
                .csrf().disable();
    }    

    @Bean(name = "passwordEncoder")    
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository()
    {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler
            savedRequestAwareAuthenticationSuccessHandler()
    {
        SavedRequestAwareAuthenticationSuccessHandler auth
                = new SavedRequestAwareAuthenticationSuccessHandler()
                {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
                    {
                        HttpSession session = request.getSession();                        
                        
                        if (session != null)
                        {
                            String redirectUrl = (String) session.getAttribute("targetUrl");

                            if (redirectUrl != null)
                            {
                                session.removeAttribute("targetUrl");
                             
                                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                            } else
                            {
                                super.onAuthenticationSuccess(request, response, authentication);
                            }
                        } else
                        {
                            super.onAuthenticationSuccess(request, response, authentication);
                        }
                    }
                };

        return auth;
    }
}
