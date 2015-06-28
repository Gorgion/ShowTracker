/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Id;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import net.sf.showtracker.constraints.PasswordsNotEqual;
import net.sf.showtracker.domain.LoginDetails;
import net.sf.showtracker.domain.UserAccount;
import net.sf.showtracker.domain.UserAccountAuth;
import net.sf.showtracker.domain.VerificationToken;
import net.sf.showtracker.exception.ServiceException;
import net.sf.showtracker.exception.UserAlreadyExistsException;
import net.sf.showtracker.security.CurrentUsername;
import net.sf.showtracker.security.service.RememberMeService;
import net.sf.showtracker.service.EmailService;
import net.sf.showtracker.service.LoginDetailsService;
import net.sf.showtracker.service.UserAccountAuthService;
import net.sf.showtracker.service.UserAccountService;
import net.sf.showtracker.service.VerificationTokenService;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Represents user related pages.
 *
 * @author TomĂˇĹˇ Svoboda
 */
@Controller
public class UserController
{

    @Autowired
    private UserAccountAuthService userAuthService;

    @Autowired
    private UserAccountService userService;

    @Autowired
    private LoginDetailsService loginDetailsService;

    @Autowired
    private RememberMeService rememberMeService;

    @Autowired
    @Qualifier("authMgr")
    private AuthenticationManager authMgr;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createAccount(Model model, HttpServletRequest request)
    {
        try
        {
            request.logout();
        } catch (ServletException ex)
        {
            throw new ServiceException("Error while user logout.", ex);
        }

        model.addAttribute("account", new UserAccountRegisterForm());

        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String creatingAccount(@Valid @ModelAttribute(value = "account") UserAccountRegisterForm accountForm, BindingResult result, HttpServletRequest request) throws Throwable// throws ExecutionException, InterruptedException, Throwable
    {

        if (result.hasErrors())
        {
            return "register";
        }

        try
        {
            try
            {
                UserAccountAuth account = accountForm.toUserAccountAuth();

                account.setPassword(passwordEncoder.encode(account.getPassword()));
                Future<Void> creation = userAuthService.create(account);

                creation.get();
                boolean autologin = loginAfterSuccessfulRegistration(account, accountForm.getPassword());//request);

                if (autologin)
                {
                    LoginDetails ld = new LoginDetails();
                    ld.setTime(Calendar.getInstance().getTime());
                    ld.setAccountId(account.getUserAccountId());

                    loginDetailsService.create(ld);
                }
            } catch (InterruptedException | ExecutionException ex)
            {
                throw ex.getCause();
            }
        } catch (UserAlreadyExistsException e)
        {
            result.rejectValue("mail", "error.exist.email");

            return "register";
        }

        return "redirect:/auth/shows";
    }

    @RequestMapping("/auth/user/edit")
    public String userProfileFormInit(Model model, HttpServletRequest request, @CurrentUsername String userEmail)
    {
        if (rememberMeService.isRememberMeAuthenticated())
        {
            rememberMeService.setRememberMeTargetUrlToSession(request, "/auth/user/edit");

            model.addAttribute("loginUpdate", true);
            model.addAttribute("email", userEmail);
            model.addAttribute("msg", "msg.login.needFullAuthentication");

            return "/login";
        }

        try
        {
            UserAccountAuth userAccount = userAuthService.getByEmail(userEmail).get();

            model.addAttribute("accountGeneral", userAccount.getUserAccount());
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Account edit error.", ex);
        }

        return "user-profile";
    }

    @RequestMapping(value = "/auth/user/edit", method = RequestMethod.POST)
    public String userProfileFormSubmit(@Valid @ModelAttribute(value = "accountGeneral") UserAccount account, BindingResult result, Model model, RedirectAttributes redirectAttributes, @CurrentUsername String userEmail)
    {
        if (result.hasErrors())
        {
            return "user-profile";
        }

        UserAccountAuth accountToUpdate;
        try
        {
            accountToUpdate = userAuthService.getByEmail(userEmail).get();

            UserAccount tmpAccount = accountToUpdate.getUserAccount();
            tmpAccount.setName(account.getName().trim());

            accountToUpdate.setUserAccount(tmpAccount);

            userAuthService.update(accountToUpdate).get();

            redirectAttributes.addFlashAttribute("msg", "msg.changessaved");
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Account edit error.", ex);

        }

        return "redirect:/auth/user/edit";
    }

    @RequestMapping(value = "/auth/user/edit/password", method = RequestMethod.GET)
    public String passwordChangeFormInit(Model model, HttpServletRequest request, @CurrentUsername String userEmail)
    {
        if (rememberMeService.isRememberMeAuthenticated())
        {
            rememberMeService.setRememberMeTargetUrlToSession(request, "/auth/user/edit");

            model.addAttribute("loginUpdate", true);
            model.addAttribute("email", userEmail);
            model.addAttribute("msg", "msg.login.needFullAuthentication");

            return "/login";
        }

        model.addAttribute("accountPassword", new UserAccountPasswordChangeForm());

        return "change-password";
    }

    @RequestMapping(value = "/auth/user/edit/password", method = RequestMethod.POST)
    public String passwordChangeFormSubmit(@Valid @ModelAttribute(value = "accountPassword") UserAccountPasswordChangeForm account, BindingResult result, Model model, RedirectAttributes redirectAttributes, @CurrentUsername String userEmail)
    {
        try
        {
            if (result.hasErrors())
            {
                return "change-password";
            }

            UserAccountAuth accountToUpdate = userAuthService.getByEmail(userEmail).get();

            accountToUpdate.setPassword(passwordEncoder.encode(account.getPassword()));

            userAuthService.update(accountToUpdate).get();

            redirectAttributes.addFlashAttribute("msg", "msg.passwordChanged");

        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Account password update error.", ex);

        }

        return "redirect:/auth/user/edit";
    }

    @RequestMapping(value = "/auth/user/statistics", method = RequestMethod.GET)
    public String accountStatistics(Model model, @CurrentUsername String userEmail)
    {
        List<LoginDetails> logins = null;
        try
        {
            UserAccount account = userService.getByEmail(userEmail).get();

            logins = loginDetailsService.getAllByAccount(account).get();

        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Error during retrieving account details.", ex);

        }

        model.addAttribute("logins", logins != null && !logins.isEmpty() ? logins : Collections.EMPTY_LIST);

        return "account-statistics";
    }

    @RequestMapping(value = "/auth/user/delete", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, @CurrentUsername String userEmail)
    {
        UserAccountAuth account = null;

        try
        {
            account = userAuthService.getByEmail(userEmail).get();

            userAuthService.delete(account).get();

        } catch (InterruptedException | ExecutionException e)
        {
            if (account != null)
            {
                model.addAttribute("accountGeneral", account.getUserAccount());
            } else
            {
                throw new ServiceException("Account deletion error.", e);

            }

            return "user-profile";
        }

        try
        {
            request.logout();
        } catch (ServletException ex)
        {
            throw new ServiceException("User logout error.", ex);

        }

        return "redirect:/";
    }

    @RequestMapping(value = "/account/lost-password")
    public String lostPassword(Model model)
    {
        model.addAttribute("emailWrapper", new EmailWrapper());
        
        return "lost-password";
    }

    @RequestMapping(value = "/account/lost-password", method = RequestMethod.POST)
    public String lostPasswordSendMessage(@Valid @ModelAttribute(value = "emailWrapper") EmailWrapper email, BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes)// throws InterruptedException, ExecutionException
    {
        if(result.hasErrors())
        {
//            redirectAttributes.addFlashAttribute("msg", "org.hibernate.validator.constraints.Email.message");
//            redirectAttributes.addFlashAttribute("errors", result.getFieldError().);
//            System.out.println("\n-----REDIR");
            return "lost-password";
//            return "redirect:/account/lost-password";
        }
//        System.out.println("\n--not redir");
        try
        {
            Locale locale = LocaleContextHolder.getLocale();

            VerificationToken token = new VerificationToken();

            String uuid = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, VerificationToken.DEFAULT_TIME_TO_LIVE);

            token.setToken(uuid);
            token.setEmail(email.getEmail());
            token.setTokenType(VerificationToken.TokenType.RESET_PASSWORD);
            token.setExpirationTime(calendar.getTime());

            verificationTokenService.create(token).get();

//            String link = emailService.generatePasswordResetLink(request.getRequestURL() + "/restore").get();
            String link = request.getRequestURL() + "/restore?token=" + token.getToken();

            String subject = messageSource.getMessage("mail.lostPassword.subject", null, locale);
            String mailMessage = messageSource.getMessage("mail.lostPassword.text", new String[]
            {
                email.getEmail(), link
            }, locale);

            emailService.send(email.getEmail(), subject, mailMessage).get();
            
//            redirectAttributes.addFlashAttribute("msg", "msg.lostPassword.emailSended");
            redirectAttributes.addFlashAttribute("msgSended", true);
            redirectAttributes.addFlashAttribute("expiration", VerificationToken.DEFAULT_TIME_TO_LIVE);
            redirectAttributes.addFlashAttribute("expirationUnit", "minutes");
//            redirectAttributes.addFlashAttribute("showOnlyMsg", true);

            return "redirect:/account/lost-password";
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Error while generating lost password message.", ex);
        }
    }

    @RequestMapping(value = "/account/lost-password/restore")
    public String lostPasswordRestore(@RequestParam(required = false) String token, Model model)
    {
        model.addAttribute("accountPassword", new UserAccountPasswordChangeForm());
        model.addAttribute("token", token);

        return "reset-lost-password";
    }

    @RequestMapping(value = "/account/lost-password/restore", method = RequestMethod.POST)
    public String lostPasswordRestoring(@RequestParam String token, @Valid @ModelAttribute(value = "accountPassword") UserAccountPasswordChangeForm account, BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        try
        {
            if (result.hasErrors())
            {
                return "reset-lost-password";
            }

            VerificationToken verificationToken = verificationTokenService.getByTokenValue(token).get();

            if ((verificationToken == null) || (verificationToken.getTokenType() != VerificationToken.TokenType.RESET_PASSWORD) || (Calendar.getInstance().after(verificationToken.getExpireTime())))
            {
                redirectAttributes.addFlashAttribute("msg", "msq.verificationToken.invalid");

                return "redirect:/account/lost-password";
            }

            UserAccountAuth accountToUpdate = userAuthService.getByEmail(verificationToken.getEmail()).get();

            if (accountToUpdate == null)
            {
                redirectAttributes.addFlashAttribute("msg", "msq.verificationToken.invalid");

                return "redirect:/account/lost-password";
            }

            accountToUpdate.setPassword(passwordEncoder.encode(account.getPassword()));

            userAuthService.update(accountToUpdate).get();

            verificationTokenService.delete(verificationToken).get();

            redirectAttributes.addFlashAttribute("msg", "msg.passwordChanged");
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Account password update error.", ex);

        }

        return "redirect:/account/lost-password/restore";
    }

    private boolean loginAfterSuccessfulRegistration(UserDetails account, final String password)//HttpServletRequest request)
    {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
        authMgr.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth.isAuthenticated();

    }

    @PasswordsNotEqual(passwordFieldName = "password", passwordVerificationFieldName = "confirmPassword")
    public static class UserAccountRegisterForm
    {
        @Size(max = 50)
        private String username;

        @NotBlank
        @Size(max = 50)
        private String password;

        @NotBlank
        @Size(max = 50)
        private String confirmPassword;

        @NotBlank
        @Size(max = 50)
        @Email
        private String mail;

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword)
        {
            this.confirmPassword = confirmPassword;
        }

        public String getMail()
        {
            return mail;
        }

        public void setMail(String mail)
        {
            this.mail = mail;
        }

        public UserAccountAuth toUserAccountAuth()
        {
            UserAccountAuth accountAuth = new UserAccountAuth();
            UserAccount account = new UserAccount();

            account.setEmail(mail);
            account.setName(username);

            accountAuth.setEnabled(true);

            accountAuth.setPassword(password);
            accountAuth.setUserAccount(account);

            return accountAuth;
        }
    }

    public static class UserAccountProfileForm
    {

        @Id
        private Long id;

        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String mail;

        public UserAccountProfileForm()
        {
        }

        public UserAccountProfileForm(UserAccount account)
        {
            this.id = account.getId();
            this.mail = account.getEmail();
            this.username = account.getName();
        }

        public Long getId()
        {
            return id;
        }

        public void setId(Long id)
        {
            this.id = id;
        }

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getMail()
        {
            return mail;
        }

        public void setMail(String mail)
        {
            this.mail = mail;
        }

    }

    @PasswordsNotEqual(passwordFieldName = "password", passwordVerificationFieldName = "confirmPassword")
    public static class UserAccountPasswordChangeForm
    {

        @Id
        private Long id;

        @NotBlank
        @Size(max = 50)
        private String password;

        @NotBlank
        @Size(max = 50)
        private String confirmPassword;

        public Long getId()
        {
            return id;
        }

        public void setId(Long id)
        {
            this.id = id;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword)
        {
            this.confirmPassword = confirmPassword;
        }
    }
    
    public static class EmailWrapper
    {
        @Email
        @NotBlank
        private String email;

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
    }
}
