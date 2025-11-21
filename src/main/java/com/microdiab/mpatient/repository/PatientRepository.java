package com.microdiab.mpatient.repository;


import com.microdiab.mpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByLastnameAndFirstnameAndDateofbirthAndGender(
            String lastname,
            String firstname,
            LocalDate dateofbirth,
            String gender
    );
}
