package com.se.exceptions;

public class VersionConflictException extends RuntimeException {
    public VersionConflictException(String msg) {
        super(msg);
    }
}
