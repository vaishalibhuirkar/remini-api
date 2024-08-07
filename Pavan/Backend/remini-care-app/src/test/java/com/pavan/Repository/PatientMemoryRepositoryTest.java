package com.pavan.Repository;

import com.pavan.Entity.PatientMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"})
public class PatientMemoryRepositoryTest {

    @Autowired
    private PatientMemoryRepository patientMemoryRepository;

    private PatientMemory patientMemory;

    @BeforeEach
    public void setUp() {
        patientMemory = new PatientMemory();
        patientMemory.setMemory_desc("Test memory description");
        patientMemory.setImage_path("test/path/to/image.jpg");
        patientMemory.setDate(java.sql.Date.valueOf("2024-08-01"));
    }

    @Test
    public void testSavePatientMemory() {
        PatientMemory savedPatientMemory = patientMemoryRepository.save(patientMemory);
        assertThat(savedPatientMemory).isNotNull();
        assertThat(savedPatientMemory.getPm_id()).isGreaterThan(0);
        assertThat(savedPatientMemory.getMemory_desc()).isEqualTo("Test memory description");
    }

    @Test
    public void testFindById() {
        PatientMemory savedPatientMemory = patientMemoryRepository.save(patientMemory);
        Optional<PatientMemory> foundPatientMemory = patientMemoryRepository.findById(savedPatientMemory.getPm_id());
        assertThat(foundPatientMemory).isPresent();
        assertThat(foundPatientMemory.get().getMemory_desc()).isEqualTo("Test memory description");
    }

    @Test
    public void testDeleteById() {
        PatientMemory savedPatientMemory = patientMemoryRepository.save(patientMemory);
        patientMemoryRepository.deleteById(savedPatientMemory.getPm_id());
        Optional<PatientMemory> foundPatientMemory = patientMemoryRepository.findById(savedPatientMemory.getPm_id());
        assertThat(foundPatientMemory).isNotPresent();
    }
}
