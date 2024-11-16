package com.acs.Shopping.Cart.exceptions;

public class ResourceUpdateException extends RuntimeException{
    public ResourceUpdateException(String message) {
        super(message);
    }

    public ResourceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
