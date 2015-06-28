/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.config;

import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Load email confuguration stored in property files.
 *
 * @author Tomáš Svoboda
 */
@Configuration
@PropertySource("classpath:config/email.properties")
public class EmailConfig
{
    @Resource
    private Environment env;

//    @Value("${email.address.from}")
//    private static final String FROM_EMAIL_ADDRESS = env.getProperty("email.address.from");
    private static final String PROPERTY_NAME_EMAIL_ADDRESS_FROM = "mail.address.from";
    private static final String PROPERTY_NAME_EMAIL_PASSWORD_FROM = "mail.password.from";

    private static final String PROPERTY_NAME_EMAIL_SMTP_HOST = "mail.smtp.host";
    private static final String PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String PROPERTY_NAME_EMAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String PROPERTY_NAME_EMAIL_SMTP_PORT = "mail.smtp.port";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "mailSession")
    public Session getMailSession()
    {

        Session session = Session.getDefaultInstance(mailProperties(),
                new javax.mail.Authenticator()
                {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(env.getRequiredProperty(PROPERTY_NAME_EMAIL_ADDRESS_FROM), env.getRequiredProperty(PROPERTY_NAME_EMAIL_PASSWORD_FROM));
                    }
                });
        return session;
    }

    private Properties mailProperties()
    {
        Properties props = new Properties();
        props.put(PROPERTY_NAME_EMAIL_SMTP_HOST, env.getRequiredProperty(PROPERTY_NAME_EMAIL_SMTP_HOST));
        props.put(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_PORT, env.getRequiredProperty(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_PORT));
        props.put(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_CLASS, env.getRequiredProperty(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_CLASS));
        props.put(PROPERTY_NAME_EMAIL_SMTP_AUTH, env.getRequiredProperty(PROPERTY_NAME_EMAIL_SMTP_AUTH));
        props.put(PROPERTY_NAME_EMAIL_SMTP_PORT, env.getRequiredProperty(PROPERTY_NAME_EMAIL_SMTP_PORT));
//        System.out.println("\n-----PROP\n" + props + "\n----------");
        return props;
    }
}
