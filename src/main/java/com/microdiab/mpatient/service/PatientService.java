package com.microdiab.mpatient.service;

import com.microdiab.mpatient.exceptions.PatientDuplicateException;
import com.microdiab.mpatient.exceptions.PatientNotFoundException;
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

    public Patient updatePatient(Long id, Patient updatePatient) {
        // Vérifiez si le patient existe
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Le patient avec l'ID " + id + " n'existe pas."));

        // Mettre à jour les informations du patient
        existingPatient.setLastname(updatePatient.getLastname());
        existingPatient.setFirstname(updatePatient.getFirstname());
        existingPatient.setDateofbirth(updatePatient.getDateofbirth());
        existingPatient.setGender(updatePatient.getGender());
        existingPatient.setAddress(updatePatient.getAddress());
        existingPatient.setPhone(updatePatient.getPhone());

        // Sauvegarder les modifications
        return patientRepository.save(existingPatient);
    }
}

