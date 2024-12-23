package com.api.AscendCargo.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class InvalidFormatException extends EntityNotFoundException {
    public InvalidFormatException(String message) {
        super(message);
    }
}
