package com.pavan.Service;

import com.pavan.Entity.PatientMemory;
import com.pavan.Repository.PatientMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientMemoryServiceTest {

    @Mock
    private PatientMemoryRepository patientMemoryRepository;

    @InjectMocks
    private PatientMemoryServiceImpl patientMemoryService;

    private PatientMemory patientMemory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientMemory = new PatientMemory();
        patientMemory.setPm_id(1);
        patientMemory.setMemory_desc("Sample memory description");
        patientMemory.setDate(new java.util.Date());
        patientMemory.setImage_path("path/to/image.jpg");
    }

    @Test
    public void testSaveMemory() {
        when(patientMemoryRepository.save(patientMemory)).thenReturn(patientMemory);

        PatientMemory savedMemory = patientMemoryService.saveMemory(patientMemory);

        assertThat(savedMemory).isNotNull();
        assertThat(savedMemory.getPm_id()).isEqualTo(1);
        assertThat(savedMemory.getMemory_desc()).isEqualTo("Sample memory description");
        verify(patientMemoryRepository, times(1)).save(patientMemory);
    }

    @Test
    public void testGetAllMemories() {
        List<PatientMemory> memories = new ArrayList<>();
        memories.add(patientMemory);

        when(patientMemoryRepository.findAll()).thenReturn(memories);

        List<PatientMemory> allMemories = patientMemoryService.getAllMemories();

        assertThat(allMemories).isNotEmpty();
        assertThat(allMemories.size()).isEqualTo(1);
        assertThat(allMemories.get(0).getMemory_desc()).isEqualTo("Sample memory description");
        verify(patientMemoryRepository, times(1)).findAll();
    }
}
