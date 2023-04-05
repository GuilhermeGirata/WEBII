
package com.web2.model.exceptions;

public class EmailNotSendException extends BusinessLogicException{

    public EmailNotSendException() {
    }

    public EmailNotSendException(String message) {
        super(message);
    }

    public EmailNotSendException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
