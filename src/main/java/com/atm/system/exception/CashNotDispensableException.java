package com.atm.system.exception;

public class CashNotDispensableException extends RuntimeException {
    public CashNotDispensableException(String message) {
        super(message);
    }
}
