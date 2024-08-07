package com.pavan.Controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientMemoryService patientMemoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadAndMatchPhoto() throws Exception {
        // Prepare test data
        PatientMemory testMemory = new PatientMemory();
        testMemory.setPm_id(1);
        testMemory.setImage_path("C:/Users/pavan/Downloads/images/testImage.jpg");

        List<PatientMemory> matchingMemories = new ArrayList<>();
        matchingMemories.add(testMemory);

        when(patientMemoryService.getAllMemories()).thenReturn(matchingMemories);

        MockMultipartFile file = new MockMultipartFile(
                "photo", "testImage.jpg", "image/jpeg", new byte[1]); // Updated byte array for a valid image

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/memory")
                        .file(file)
                        .param("memoryDesc", "Test Memory Description")
                        .param("date", "2024-08-01"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pm_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image_path").value("C:/Users/pavan/Downloads/images/testImage.jpg"))
                .andDo(MockMvcResultHandlers.print());
    }
}
