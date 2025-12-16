package com.microdiab.mpatient.controller;


import com.microdiab.mpatient.exceptions.PatientNotFoundException;
import com.microdiab.mpatient.repository.PatientRepository;
import com.microdiab.mpatient.model.Patient;
import com.microdiab.mpatient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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


    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> showPatientList() {
        List<Patient> patients = patientRepository.findAll();

        log.info("Patients size = {}", patients.size());

        return ResponseEntity.ok(patients);
    }


    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> showPatientId(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        log.warn("Patient non trouvé pour l'ID : {}", id);
        if (patient.isEmpty()) throw new PatientNotFoundException("The patient corresponding to the ID " + id + " does not exist.");

        log.info("Patient founded : {}", patient.get().getLastname());
        log.info("Patient dateofbirth : {}", patient.get().getDateofbirth());

        return ResponseEntity.ok(patient.get());
    }


    @PostMapping("/patient")
    public ResponseEntity<?> addPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        // ResponseEntity<?> : Permet de retourner soit le patient sauvegardé, soit une erreur.
        log.info("Ajout d'un nouveau patient : {}", patient.getLastname());

        if (result.hasErrors()) {
            // Retourne les erreurs de validation
            log.info("Patient non sauvegardé, erreur de validation : {}", result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Si pas d'erreur de validation, sauvegarde le patient. Les autres erreurs de validation sont gérés par le globalexceptionhandler)
        Patient savedPatient = patientService.savePatient(patient);
        log.info("Patient sauvegardé avec l'ID : {}", savedPatient.getId());
        return ResponseEntity.ok(savedPatient);
    }


    @PutMapping("/patient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient updatePatient, BindingResult result) {

        log.info("Mise à jour du patient avec l'ID : {}", id);

        if (result.hasErrors()) {
            // Retourne les erreurs de validation
            log.info("Patient non sauvegardé, erreur de validation : {}", result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Patient updatedPatient = patientService.updatePatient(id, updatePatient);
        log.info("Patient mis à jour avec succès : {}", updatedPatient.getLastname());
        return ResponseEntity.ok(updatedPatient);
    }

}
