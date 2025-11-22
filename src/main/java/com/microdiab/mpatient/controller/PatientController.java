package com.microdiab.mpatient.controller;


import com.microdiab.mpatient.configurations.ApplicationPropertiesConfiguration;
import com.microdiab.mpatient.exceptions.PatientNotFoundException;
import com.microdiab.mpatient.repository.PatientRepository;
import com.microdiab.mpatient.model.Patient;
import com.microdiab.mpatient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;


    @GetMapping("/")
    public String showHome() {
        return "Hello, this is patient home !!";
    }


    @GetMapping("/patients")
    public List<Patient> showPatientList() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) throw new PatientNotFoundException("No patients are registered in the list.");

        log.info("Patients size = {}", patients.size());
        int i = 0;
        for (Patient patient : patients) {
            log.info("{} - patient : {}", i, patient.getLastname());
            i++;
        }

        if (appProperties.getLimitDePatients() > 0) {
            return patients.subList(0, appProperties.getLimitDePatients());
        }

        return patients;
    }


    @GetMapping("/patient/{id}")
    public Optional<Patient> showPatientId(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) throw new PatientNotFoundException("The patient corresponding to the ID " + id + " does not exist.");

        log.info("Patient founded : {}", patient.get().getLastname());
        log.info("Patient dateofbirth : {}", patient.get().getDateofbirth());

        return patient;
    }


    @PostMapping("/patient")
    public Patient addPatient(@Valid @RequestBody Patient patient) {
        log.info("Ajout d'un nouveau patient : {}", patient.getLastname());
        Patient savedPatient = patientService.savePatient(patient);
        log.info("Patient sauvegardé avec l'ID : {}", savedPatient.getId());

        return savedPatient;
    }


    @PutMapping("/patient/{id}")
    public Patient updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patientDetails) {
        log.info("Mise à jour du patient avec l'ID : {}", id);

        // Vérifiez si le patient existe
        // NOTE : Ce doit être le service qui s'occupe de cette verification
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Le patient avec l'ID " + id + " n'existe pas."));

        // Mettez à jour les informations du patient
        existingPatient.setLastname(patientDetails.getLastname());
        existingPatient.setFirstname(patientDetails.getFirstname());
        existingPatient.setDateofbirth(patientDetails.getDateofbirth());
        existingPatient.setGender(patientDetails.getGender());
        existingPatient.setAddress(patientDetails.getAddress());
        existingPatient.setPhone(patientDetails.getPhone());

        // Sauvegardez les modifications
        Patient updatedPatient = patientRepository.save(existingPatient);
        log.info("Patient mis à jour avec succès : {}", updatedPatient.getLastname());

        return updatedPatient;
    }

}
