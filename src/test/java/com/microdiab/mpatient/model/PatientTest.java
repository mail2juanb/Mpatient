package com.microdiab.mpatient.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Vérifie que le constructeur et les getters fonctionnent correctement.
    @Test
    void testPatientConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String lastname = "Dupont";
        String firstname = "Jean";
        LocalDate dateofbirth = LocalDate.of(1980, 1, 1);
        String gender = "M";
        String address = "123 Rue de Paris";
        String phone = "0123456789";

        // Act
        Patient patient = new Patient(id, lastname, firstname, dateofbirth, gender, address, phone);

        // Assert
        assertEquals(id, patient.getId());
        assertEquals(lastname, patient.getLastname());
        assertEquals(firstname, patient.getFirstname());
        assertEquals(dateofbirth, patient.getDateofbirth());
        assertEquals(gender, patient.getGender());
        assertEquals(address, patient.getAddress());
        assertEquals(phone, patient.getPhone());
    }

    // Vérifie que les setters modifient correctement les attributs.
    @Test
    void testPatientSetters() {
        // Arrange
        Patient patient = new Patient();
        Long id = 1L;
        String lastname = "Martin";
        String firstname = "Pierre";
        LocalDate dateofbirth = LocalDate.of(1990, 5, 15);
        String gender = "M";
        String address = "456 Rue de Lyon";
        String phone = "0987654321";

        // Act
        patient.setId(id);
        patient.setLastname(lastname);
        patient.setFirstname(firstname);
        patient.setDateofbirth(dateofbirth);
        patient.setGender(gender);
        patient.setAddress(address);
        patient.setPhone(phone);

        // Assert
        assertEquals(id, patient.getId());
        assertEquals(lastname, patient.getLastname());
        assertEquals(firstname, patient.getFirstname());
        assertEquals(dateofbirth, patient.getDateofbirth());
        assertEquals(gender, patient.getGender());
        assertEquals(address, patient.getAddress());
        assertEquals(phone, patient.getPhone());
    }

    // Vérifie qu’un objet Patient valide ne génère aucune violation de contrainte.
    @Test
    void testPatientValidationSuccess() {
        // Arrange
        Patient patient = new Patient(
                1L,
                "Dupont",
                "Jean",
                LocalDate.of(1980, 1, 1),
                "M",
                "123 Rue de Paris",
                "0123456789"
        );

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Assert
        assertTrue(violations.isEmpty());
    }

    // Vérifie qu’un objet Patient invalide génère les violations attendues (ex : lastname vide, firstname null, dateofbirth dans le futur, etc.).
    @Test
    void testPatientValidationFailure() {
        // Arrange
        Patient patient = new Patient();
        patient.setLastname(""); // lastname vide
        patient.setFirstname(null); // firstname null
        patient.setDateofbirth(LocalDate.now().plusDays(1)); // dateofbirth dans le futur
        patient.setGender(""); // gender vide

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size()); // 4 violations attendues
    }

    // Test de validation : lastname vide
    @Test
    void testLastnameBlank() {
        Patient patient = new Patient();
        patient.setLastname(""); // lastname vide
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("lastname is mandatory")));
    }

    // Test de validation : lastname null
    @Test
    void testLastnameNull() {
        Patient patient = new Patient();
        patient.setLastname(null); // lastname null
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("lastname is mandatory")));
    }

    // Test de validation : firstname vide
    @Test
    void testFirstnameBlank() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname(""); // firstname vide
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("firstname is mandatory")));
    }

    // Test de validation : firstname null
    @Test
    void testFirstnameNull() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname(null); // firstname null
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("firstname is mandatory")));
    }

    // Test de validation : dateofbirth null
    @Test
    void testDateofbirthNull() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(null); // dateofbirth null
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("dateofbirth is mandatory")));
    }

    // Test de validation : dateofbirth dans le futur
    @Test
    void testDateofbirthInFuture() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.now().plusDays(1)); // dateofbirth dans le futur
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("dateofbirth must be in the past")));
    }

    // Test de validation : dateofbirth égale à aujourd'hui (limite du passé)
    @Test
    void testDateofbirthToday() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.now()); // dateofbirth = aujourd'hui
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("dateofbirth must be in the past")));
    }

    // Test de validation : gender vide
    @Test
    void testGenderBlank() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender(""); // gender vide

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("gender is mandatory")));
    }

    // Test de validation : gender null
    @Test
    void testGenderNull() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender(null); // gender null

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("gender is mandatory")));
    }

    // Test de validation : tous les champs invalides
    @Test
    void testAllFieldsInvalid() {
        Patient patient = new Patient();
        patient.setLastname(""); // lastname vide
        patient.setFirstname(null); // firstname null
        patient.setDateofbirth(LocalDate.now().plusDays(1)); // dateofbirth dans le futur
        patient.setGender(""); // gender vide

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size()); // 4 violations attendues
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("lastname is mandatory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("firstname is mandatory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("dateofbirth must be in the past")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("gender is mandatory")));
    }

    // Test de validation : tous les champs valides (cas nominal)
    @Test
    void testAllFieldsValid() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1)); // dateofbirth dans le passé
        patient.setGender("M");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertTrue(violations.isEmpty()); // Aucune violation attendue
    }
}