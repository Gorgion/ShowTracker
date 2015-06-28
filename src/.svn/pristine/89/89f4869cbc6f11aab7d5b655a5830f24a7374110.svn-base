/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.service;

import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import net.sf.showtracker.domain.VerificationToken;
import net.sf.showtracker.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomáš Svoboda
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService
{
    @Autowired
//    @Qualifier(value = "mailSession")
    private Session session;
    
    @Autowired
    private VerificationTokenService tokenService;    
    
//    @Resource
//    private Environment env;
//    
    @Value("${mail.address.from}")
    private String FROM_EMAIL_ADDRESS;// = env.getRequiredProperty("mail.address.from");
//    private static final String RESTORE_PASSWORD_LINK = env.getRequiredProperty("restorePassword.link");
    
//    private static final String PROPERTY_NAME_EMAIL_SMTP_HOST = "email.smtp.host";
//    private static final String PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_PORT = "email.smtp.socketFactory.port";
//    private static final String PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_CLASS = "email.smtp.socketFactory.class";
//    private static final String PROPERTY_NAME_EMAIL_SMTP_AUTH = "email.smtp.auth";
//    private static final String PROPERTY_NAME_EMAIL_SMTP_PORT = "email.smtp.port";
    
    @Async
    @Override
    public Future<Void> send(String to, String subject, String text)
    {
//        String to = "sonoojaiswal1987@gmail.com";//change accordingly  
//        String FROM_EMAIL_ADDRESS = "showtracker.librenet@gmail.com";
        //Get the session object  
//        Properties props = new Properties();
//        props.put(PROPERTY_NAME_EMAIL_SMTP_HOST, "smtp.gmail.com");
//        props.put(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_PORT, "465");
//        props.put(PROPERTY_NAME_EMAIL_SMTP_SOCKETFACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
//        props.put(PROPERTY_NAME_EMAIL_SMTP_AUTH, "true");
//        props.put(PROPERTY_NAME_EMAIL_SMTP_PORT, "465");
//        
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator()
//                {
//                    protected PasswordAuthentication getPasswordAuthentication()
//                    {
//                        return new PasswordAuthentication("showtracker.librenet@gmail.com", "123qwe456asd");//change accordingly  
////                        return new PasswordAuthentication("yourgmailid@gmail.com", "password");//change accordingly  
//                    }
//                });

//        System.out.println("\n--sesion:" + session + "<");
//        System.out.println(session.getProperties());
        
//        try
//        {
//            System.out.println("\n" + session.getStore());
//        } catch (NoSuchProviderException ex)
//        {
//            System.out.println(">>" + ex);
//        }
//        
//        {            
//            System.out.println("\n" + session.getProviders());         
//        }
//        try
//        {
//            System.out.println("\n" + session.getTransport());
//        } catch (NoSuchProviderException ex)
//        {
//            System.out.println(">>" + ex);
//        }
        
//        System.out.println("\n--FROM: " + FROM_EMAIL_ADDRESS);
//        System.out.println("to: " + to);
        //compose message  
        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
//            System.out.println("--before send");
            Transport.send(message);
//            System.out.println("--after send");
            return new AsyncResult<>(null);
        } catch (MessagingException e)
        {
            throw new ServiceException("Error while sending message.",e);
        }
    }
    
    @Override
    public Future<String> generatePasswordResetLink(String baseLink)
    {               
        VerificationToken token = new VerificationToken();
        
        String uuid = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, VerificationToken.DEFAULT_TIME_TO_LIVE);                
        
        token.setToken(uuid);
        token.setTokenType(VerificationToken.TokenType.RESET_PASSWORD);
        token.setExpirationTime(calendar.getTime());
        
        String link = baseLink + "?token=" + token.getToken();
        
        tokenService.create(token);
        
        return new AsyncResult<>(link);
    }
}
