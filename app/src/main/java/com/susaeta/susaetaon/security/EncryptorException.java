package com.susaeta.susaetaon.security;

public class EncryptorException extends RuntimeException {
    private static final long serialVersionUID = -6287879474376373646L;

    public EncryptorException(String message) {
        super(message);
    }

    public EncryptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptorException(Throwable cause) {
        super(cause);
    }
}