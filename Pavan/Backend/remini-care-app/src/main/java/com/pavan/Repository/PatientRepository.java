package com.pavan.Repository;

import com.pavan.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    List<Patient> findByDisorders(String disorder);
}
