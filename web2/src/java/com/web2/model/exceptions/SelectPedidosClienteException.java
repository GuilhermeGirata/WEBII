
package com.web2.model.exceptions;

public class SelectPedidosClienteException extends BusinessLogicException{

    public SelectPedidosClienteException() {
    }

    public SelectPedidosClienteException(String message) {
        super(message);
    }

    public SelectPedidosClienteException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
