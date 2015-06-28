/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.config;

import java.security.Principal;
import net.sf.showtracker.security.CurrentUsername;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Configuration for CurrentUsername interface.
 *
 * @author Tomáš Svoboda
 */
public class CurrentUsernameHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{

    @Override
    public boolean supportsParameter(MethodParameter methodParameter)
    {
        return methodParameter.getParameterAnnotation(CurrentUsername.class) != null
                && methodParameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception
    {

        if (this.supportsParameter(methodParameter))
        {
            Principal principal = webRequest.getUserPrincipal();

            return principal.getName();
        } else
        {
            return WebArgumentResolver.UNRESOLVED;
        }
    }
}
