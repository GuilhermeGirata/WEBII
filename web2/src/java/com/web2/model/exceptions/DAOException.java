
package com.web2.model.exceptions;


public class DAOException extends AppException{

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        
        super(message, cause);
    }

   
}
