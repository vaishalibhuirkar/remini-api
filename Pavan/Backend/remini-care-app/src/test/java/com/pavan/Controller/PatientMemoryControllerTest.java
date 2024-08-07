package com.pavan.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavan.Entity.PatientMemory;
import com.pavan.Service.PatientMemoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientMemoryController.class)
public class PatientMemoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientMemoryService patientMemoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadMemory() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test Image Content".getBytes(StandardCharsets.UTF_8)
        );

        PatientMemory patientMemory = new PatientMemory();
        patientMemory.setMemory_desc("Test Memory");
        patientMemory.setDate(new Date());
        patientMemory.setImage_path("C:/Users/pavan/Downloads/images/test-image.jpg");

        // Mock the saveMemory method to return the expected PatientMemory object
        when(patientMemoryService.saveMemory(any(PatientMemory.class))).thenReturn(patientMemory);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/memory/upload")
                        .file(image)
                        .param("memoryDesc", "Test Memory")
                        .param("date", "2024-08-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.memory_desc").value("Test Memory"))
                .andExpect(jsonPath("$.image_path").value("C:/Users/pavan/Downloads/images/test-image.jpg"));
    }


    @Test
    public void testGetAllMemories() throws Exception {
        PatientMemory memory1 = new PatientMemory();
        memory1.setMemory_desc("Memory 1");
        memory1.setDate(new Date());
        memory1.setImage_path("C:/Users/pavan/Downloads/images/memory1.jpg");

        PatientMemory memory2 = new PatientMemory();
        memory2.setMemory_desc("Memory 2");
        memory2.setDate(new Date());
        memory2.setImage_path("C:/Users/pavan/Downloads/images/memory2.jpg");

        when(patientMemoryService.getAllMemories()).thenReturn(Arrays.asList(memory1, memory2));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/memory/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].memory_desc").value("Memory 1"))
                .andExpect(jsonPath("$[1].memory_desc").value("Memory 2"));
    }
}
