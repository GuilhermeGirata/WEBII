
package com.web2.model.exceptions;


public class DeleteRoupaException extends BusinessLogicException{

    public DeleteRoupaException() {
    }

    public DeleteRoupaException(String message) {
        super(message);
    }

    public DeleteRoupaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
