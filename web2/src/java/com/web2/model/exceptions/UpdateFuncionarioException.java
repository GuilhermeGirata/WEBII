
package com.web2.model.exceptions;


public class UpdateFuncionarioException extends BusinessLogicException{

    public UpdateFuncionarioException() {
    }

    public UpdateFuncionarioException(String message) {
        super(message);
    }

    public UpdateFuncionarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
