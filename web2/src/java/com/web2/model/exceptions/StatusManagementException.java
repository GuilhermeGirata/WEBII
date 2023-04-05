
package com.web2.model.exceptions;

/**
 *
 * @author jeffe
 */
public class StatusManagementException extends BusinessLogicException{

    public StatusManagementException() {
    }

    public StatusManagementException(String message) {
        super(message);
    }

    public StatusManagementException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
