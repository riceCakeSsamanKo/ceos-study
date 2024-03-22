package com.ceos19.everytime.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DATA_ALREADY_EXISTED(CONFLICT, ""),
    NO_DATA_EXISTED(NOT_FOUND, ""),

    ID_DUPLICATED(CONFLICT, ""),
    DATA_NOT_FOUND(NOT_FOUND, ""),
    INVALID_PASSWORD(UNAUTHORIZED, ""),

    NO_DATA_ALLOCATED(FAILED_DEPENDENCY, ""),

    KEYWORD_TOO_SHORT(BAD_REQUEST, ""),
    INVALID_VALUE_ASSIGNMENT(BAD_REQUEST, ""),
    INVALID_URI_ACCESS(NOT_FOUND,"");


    private final HttpStatus httpStatus;
    private final String message;
}
