
package com.web2.model.exceptions;

public class InsertFuncionarioException extends BusinessLogicException{

    public InsertFuncionarioException() {
    }

    public InsertFuncionarioException(String message) {
        super(message);
    }

    public InsertFuncionarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
