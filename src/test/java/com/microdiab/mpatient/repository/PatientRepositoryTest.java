package com.microdiab.mpatient.repository;

import com.microdiab.mpatient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest            // Configure un contexte Spring minimal pour les tests de la couche JPA.
@ActiveProfiles("test") // Optionnel : pour utiliser un profil spécifique aux tests
public class PatientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;        // Permet de manipuler les entités en base de données de test (H2 par défaut).

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void whenPatientExists_thenReturnTrue() {
        // Arrange
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        patient.setFirstname("Jean");
        patient.setDateofbirth(LocalDate.of(1990, 1, 1));
        patient.setGender("M");
        entityManager.persist(patient);     // Sauvegarde un patient en base pour le test.
        entityManager.flush();              // Force la synchronisation avec la base de données.

        // Act
        boolean exists = patientRepository.existsByLastnameAndFirstnameAndDateofbirthAndGender(
                "Dupont", "Jean", LocalDate.of(1990, 1, 1), "M");

        // Assert
        assertThat(exists).isTrue();        // Vérifie que la méthode retourne true si le patient existe.
    }

    @Test
    public void whenPatientDoesNotExist_thenReturnFalse() {
        // Act
        boolean exists = patientRepository.existsByLastnameAndFirstnameAndDateofbirthAndGender(
                "Unknown", "User", LocalDate.of(2000, 1, 1), "F");

        // Assert
        assertThat(exists).isFalse();       // Vérifie que la méthode retourne false si le patient n’existe pas.
    }
}


