package com.mexpedition.web.exceptions;

public class ExpeditionNotFoundException extends RuntimeException {
    public ExpeditionNotFoundException(String message){
        super(message);
    }

}
