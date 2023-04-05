
package com.web2.model.exceptions;


public class DeleteFuncionarioException extends BusinessLogicException{

    public DeleteFuncionarioException() {
    }

    public DeleteFuncionarioException(String message) {
        super(message);
    }

    public DeleteFuncionarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
