package com.microdiab.mpatient.service;

import com.microdiab.mpatient.exceptions.PatientDuplicateException;
import com.microdiab.mpatient.model.Patient;
import com.microdiab.mpatient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        // Vérifie si un patient avec les mêmes critères existe déjà
        if (patientRepository.existsByLastnameAndFirstnameAndDateofbirthAndGender(
                patient.getLastname(),
                patient.getFirstname(),
                patient.getDateofbirth(),
                patient.getGender())) {
            throw new PatientDuplicateException ("A patient already exists with the same last name, first name, date of birth, and gender.");
        }
        // Sauvegarde le patient
        return patientRepository.save(patient);
    }
}

