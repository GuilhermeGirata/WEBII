/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.exceptions;

/**
 *
 * @author jeffe
 */
public class AccountExistException extends BusinessLogicException{

    public AccountExistException() {
    }

    public AccountExistException(String message) {
        super(message);
    }

    public AccountExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
