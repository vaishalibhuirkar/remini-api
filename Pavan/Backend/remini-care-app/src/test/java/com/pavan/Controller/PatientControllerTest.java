package com.pavan.Controller;

import com.pavan.Entity.Patient;
import com.pavan.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void testSavePatient() throws Exception {
        Patient patient = new Patient(1, "John Doe", "Dementia", 65, "123 Main St", "12345");
        when(patientService.createPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/admin/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"disorders\": \"Dementia\", \"age\": 65, \"address\": \"123 Main St\", \"pincode\": \"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }


    @Test
    void testGetAllPatients() throws Exception {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "John Doe", "Dementia", 65, "123 Main St", "12345"),
                new Patient(2, "Jane Doe", "Alzheimer's", 70, "456 Elm St", "67890")
        );
        when(patientService.getAllPatient()).thenReturn(patients);

        mockMvc.perform(get("/admin/patient/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void testGetPatientsByDisorder() throws Exception {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "John Doe", "Dementia", 65, "123 Main St", "12345")
        );
        when(patientService.getAllPatientsByDisorder("Dementia")).thenReturn(patients);

        mockMvc.perform(get("/admin/patient/disorder/Dementia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetPatientById() throws Exception {
        Patient patient = new Patient(1, "John Doe", "Dementia", 65, "123 Main St", "12345");
        when(patientService.getPatientById(1)).thenReturn(patient);

        mockMvc.perform(get("/admin/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/admin/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient deleted with id: 1"));
    }
}

