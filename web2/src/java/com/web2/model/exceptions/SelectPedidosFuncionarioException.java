
package com.web2.model.exceptions;


public class SelectPedidosFuncionarioException extends BusinessLogicException{

    public SelectPedidosFuncionarioException() {
    }

    public SelectPedidosFuncionarioException(String message) {
        super(message);
    }

    public SelectPedidosFuncionarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
