
package com.web2.model.exceptions;


public class LogicaLoginException extends BusinessLogicException{

    public LogicaLoginException() {
    }

    public LogicaLoginException(String message) {
        super(message);
    }

    public LogicaLoginException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
