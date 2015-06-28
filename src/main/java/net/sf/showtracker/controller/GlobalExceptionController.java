/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.controller;

import net.sf.showtracker.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Represents controller for exception handling.
 *
 * @author Tomáš Svoboda
 */
@ControllerAdvice
public class GlobalExceptionController
{

    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(ServiceException ex)
    {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorCode", "500");

        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex)
    {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorCode", "500");

        return model;
    }
}
