
package com.web2.model.exceptions;


public class BDException extends DAOException{

    public BDException() {
    }

    public BDException(String message) {
        super(message);
    }

    public BDException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
