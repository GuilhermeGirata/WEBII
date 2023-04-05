
package com.web2.model.exceptions;


public class SelectFuncionarioException extends BusinessLogicException{

    public SelectFuncionarioException() {
    }

    public SelectFuncionarioException(String message) {
        super(message);
    }

    public SelectFuncionarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
