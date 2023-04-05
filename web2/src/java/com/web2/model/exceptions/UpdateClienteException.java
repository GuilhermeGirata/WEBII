/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.exceptions;

/**
 *
 * @author jeffe
 */
public class UpdateClienteException extends BusinessLogicException{

    public UpdateClienteException() {
    }

    public UpdateClienteException(String message) {
        super(message);
    }

    public UpdateClienteException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
