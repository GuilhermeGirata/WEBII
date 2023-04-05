
package com.web2.model.exceptions;


public class ChangeStatusErrorException extends BusinessLogicException{

    public ChangeStatusErrorException() {
    }

    public ChangeStatusErrorException(String message) {
        super(message);
    }

    public ChangeStatusErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
