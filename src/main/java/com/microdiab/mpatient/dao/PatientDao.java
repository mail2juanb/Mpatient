package com.microdiab.mpatient.dao;


import com.microdiab.mpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {
}
