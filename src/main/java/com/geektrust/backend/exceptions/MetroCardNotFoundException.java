package com.geektrust.backend.exceptions;

public class MetroCardNotFoundException extends RuntimeException {
    public MetroCardNotFoundException(String message) {
        super(message);
    }

    public MetroCardNotFoundException() {
        super();
    }
}
