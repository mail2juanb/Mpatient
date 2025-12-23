package com.microdiab.mpatient.exception;

import com.microdiab.mpatient.exceptions.PatientNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientNotFoundExceptionTest {

    // Vérifier que l'exception est bien levée avec le bon message.
    @Test
    public void testPatientNotFoundException_Message() {
        // Arrange
        String expectedMessage = "Patient not found with ID: 1";

        // Act & Assert
        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> { throw new PatientNotFoundException(expectedMessage); }
        );

        // Vérifie que le message est correct
        assertEquals(expectedMessage, exception.getMessage());
    }
}
