package com.microdiab.Mpatient.web.controller;


import com.microdiab.Mpatient.configurations.ApplicationPropertiesConfiguration;
import com.microdiab.Mpatient.web.exceptions.PatientNotFoundException;
import com.microdiab.Mpatient.dao.PatientDao;
import com.microdiab.Mpatient.model.Patient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientDao patientDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;


    @GetMapping("/")
    public String showHome() {
        return "Hello, this is patient home !!";
    }


    @GetMapping("/Patients")
    public List<Patient> showPatientList() {
        List<Patient> patients = patientDao.findAll();
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


    @GetMapping("/Patient/{id}")
    public Optional<Patient> showPatientId(@PathVariable Long id) {
        Optional<Patient> patient = patientDao.findById(id);
        if (patient.isEmpty()) throw new PatientNotFoundException("The patient corresponding to the ID " + id + " does not exist.");

        log.info("Patient founded : {}", patient.get().getLastname());
        log.info("Patient dateofbirth : {}", patient.get().getDateofbirth());

        return patient;
    }


}
