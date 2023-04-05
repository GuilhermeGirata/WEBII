
package com.web2.model.exceptions;

public class PrecoTotalException extends BusinessLogicException{

    public PrecoTotalException() {
    }

    public PrecoTotalException(String message) {
        super(message);
    }

    public PrecoTotalException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
