/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.exception;

/**
 * Represents severe service exception.
 *
 * @author Tomáš Svoboda
 */
public class ServiceException extends RuntimeException
{

    public ServiceException()
    {
        super();
    }

    public ServiceException(String msg)
    {
        super(msg);
    }

    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
