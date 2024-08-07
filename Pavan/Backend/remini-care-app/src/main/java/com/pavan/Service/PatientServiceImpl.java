package com.pavan.Service;

import com.pavan.Entity.Patient;
import com.pavan.Exception.CustomException;
import com.pavan.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        try{
            return patientRepository.save(patient);
        }catch (DataIntegrityViolationException | IllegalArgumentException exception ){
            throw new CustomException("Invalid Patient data "+exception.getMessage());
        }catch (Exception exception){
            throw new CustomException("An unexpected error occurred while creating the patient: " + exception.getMessage());
        }
    }

    @Override
    public Patient getPatientById(int id) {
        Optional<Patient> res = patientRepository.findById(id);
        if (res.isEmpty()) {
            throw new CustomException("Patient not found with Id: " + id);
        }
        return res.get();
    }

    @Override
    public List<Patient> getAllPatient() {
        List<Patient> res = patientRepository.findAll();
        if(res.isEmpty()){
            throw new CustomException("No Patient Found");
        }
        return res;
    }

    @Override
    public List<Patient> getAllPatientsByDisorder(String disorder) {
        List<Patient> res = patientRepository.findByDisorders(disorder);
        if(res.isEmpty()){
            throw new CustomException("No Patient found with disorder: "+disorder);
        }
        return res;
    }

    @Override
    public void deletePatientById(int id) {
        if (!patientRepository.existsById(id)) {
            throw new CustomException("Patient not found with Id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
