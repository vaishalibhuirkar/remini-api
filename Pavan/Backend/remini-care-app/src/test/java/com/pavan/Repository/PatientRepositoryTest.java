package com.pavan.Repository;

import com.pavan.Entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"})
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        patient.setName("John Doe");
        patient.setDisorders("Flu");
        patient.setAge(30);
        patient.setAddress("123 Main St");
        patient.setPincode("12345");
    }

    @Test
    public void testSavePatient() {
        Patient savedPatient = patientRepository.save(patient);
        assertThat(savedPatient).isNotNull();
        assertThat(savedPatient.getP_id()).isGreaterThan(0);
        assertThat(savedPatient.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindById() {
        Patient savedPatient = patientRepository.save(patient);
        Optional<Patient> foundPatient = patientRepository.findById(savedPatient.getP_id());
        assertThat(foundPatient).isPresent();
        assertThat(foundPatient.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByDisorders() {
        patientRepository.save(patient);
        List<Patient> patients = patientRepository.findByDisorders("Flu");
        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testDeleteById() {
        Patient savedPatient = patientRepository.save(patient);
        patientRepository.deleteById(savedPatient.getP_id());
        Optional<Patient> foundPatient = patientRepository.findById(savedPatient.getP_id());
        assertThat(foundPatient).isNotPresent();
    }
}
