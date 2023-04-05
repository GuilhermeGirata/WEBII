
package com.web2.model.exceptions;


public class ViolacaoConstraintException extends DAOException{
    
    public ViolacaoConstraintException() {
    }

    public ViolacaoConstraintException(String message) {
        super(message);
    }

    public ViolacaoConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
