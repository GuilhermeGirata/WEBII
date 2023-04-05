
package com.web2.model.exceptions;


public class GetAllRoupasException extends BusinessLogicException{

    public GetAllRoupasException() {
    }

    public GetAllRoupasException(String message) {
        super(message);
    }

    public GetAllRoupasException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
