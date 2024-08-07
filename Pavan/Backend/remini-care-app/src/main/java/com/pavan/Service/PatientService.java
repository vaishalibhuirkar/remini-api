package com.pavan.Service;

import com.pavan.Entity.Patient;

import java.util.List;

public interface PatientService {

    Patient createPatient(Patient patient);

    Patient getPatientById(int id);

    List<Patient> getAllPatient();

    List<Patient> getAllPatientsByDisorder(String disorder);

    void deletePatientById(int id);
}
