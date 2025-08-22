package com.microdiab.Mpatient.dao;


import com.microdiab.Mpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {
}
