package com.pavan.Controller;

import com.pavan.Entity.PatientMemory;
import com.pavan.Exception.CustomException;
import com.pavan.Service.PatientMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientMemoryController {

    @Autowired
    private PatientMemoryService patientMemoryService;

    @PostMapping("/memory/upload")
    public PatientMemory uploadMemory(
            @RequestParam("image") MultipartFile image,
            @RequestParam("memoryDesc") String memoryDesc,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        String imageStoragePath = "C:/Users/pavan/Downloads/images/";
        Path path = Paths.get(imageStoragePath);
        String imagePath = imageStoragePath + image.getOriginalFilename();

        try {
            File file = new File(imagePath);
            // Ensure directory exists
            if (!path.toFile().exists()) {
                path.toFile().mkdirs();
            }
            // Transfer the file to the target location
            image.transferTo(file);

            PatientMemory patientMemory = new PatientMemory();
            patientMemory.setMemory_desc(memoryDesc);
            patientMemory.setDate(date);
            patientMemory.setImage_path(imagePath);

            return patientMemoryService.saveMemory(patientMemory);

        } catch (IOException e) {
            throw new CustomException("Error uploading image file: " + e.getMessage());
        }
    }

    @GetMapping("/memory/getAll")
    public List<PatientMemory> getAllMemories(){
        return patientMemoryService.getAllMemories();
    }
}
