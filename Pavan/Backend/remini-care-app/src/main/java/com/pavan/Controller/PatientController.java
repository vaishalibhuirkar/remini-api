package com.pavan.Controller;

import com.pavan.Entity.Patient;
import com.pavan.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/patient")
    public Patient savePatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @GetMapping("/patient/getAll")
    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    @GetMapping("/patient/disorder/{disorder}")
    public List<Patient> getPatientsByDisorder(@PathVariable String disorder){
        return patientService.getAllPatientsByDisorder(disorder);
    }

    @GetMapping("/patient/{id}")
    public Patient getPatient(@PathVariable int id){
        return patientService.getPatientById(id);
    }

    @DeleteMapping("/patient/{id}")
    public String deletePatient(@PathVariable int id){
        patientService.deletePatientById(id);
        return "Patient deleted with id: " + id;
    }

}
