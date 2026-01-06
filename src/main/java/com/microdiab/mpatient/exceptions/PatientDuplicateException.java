package com.microdiab.mpatient.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409 Conflict de doublons de patient
@ResponseStatus(HttpStatus.CONFLICT)
public class PatientDuplicateException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(PatientDuplicateException.class);

    public PatientDuplicateException(String message) {
        super(message);
        log.warn("*****  THROW Exception : {} - message : {}", getClass().getName(), getMessage());
    }
}
