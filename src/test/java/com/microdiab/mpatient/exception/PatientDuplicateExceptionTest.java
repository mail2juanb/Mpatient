package com.microdiab.mpatient.exception;

import com.microdiab.mpatient.exceptions.PatientDuplicateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientDuplicateExceptionTest {

    // Vérifier que l'exception est bien levée avec le bon message.
    @Test
    public void testPatientDuplicateException_Message() {
        // Arrange
        String expectedMessage = "Duplicate patient with name coco";

        // Act & Assert
        PatientDuplicateException exception = assertThrows(
                PatientDuplicateException.class,
                () -> { throw new PatientDuplicateException(expectedMessage); }
        );

        // Vérifie que le message est correct
        assertEquals(expectedMessage, exception.getMessage());
    }
}
