/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web2.model.exceptions;

/**
 *
 * @author jeffe
 */
public class CpfExistException extends BusinessLogicException{

    public CpfExistException() {
    }

    public CpfExistException(String message) {
        super(message);
    }

    public CpfExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
