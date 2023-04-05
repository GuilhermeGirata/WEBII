
package com.web2.model.exceptions;


public class InsertPedidoException extends BusinessLogicException{

    public InsertPedidoException() {
    }

    public InsertPedidoException(String message) {
        super(message);
    }

    public InsertPedidoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
