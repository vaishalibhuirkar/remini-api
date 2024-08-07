package com.pavan.Controller;

import com.pavan.Entity.PatientMemory;
import com.pavan.Service.PatientMemoryService;
import org.opencv.core.*;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PatientMemoryService patientMemoryService;

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @PostMapping("/memory")
    public List<PatientMemory> uploadAndMatchPhoto(
            @RequestParam("image") MultipartFile image) {

        // Define the path for saving the uploaded image
        String imageStoragePath = "C:/Users/pavan/Downloads/images/";
        File directory = new File(imageStoragePath);

        // Ensure the directory exists
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String imagePath = imageStoragePath + image.getOriginalFilename();
        File tempFile = new File(imagePath);

        try {
            // Save the uploaded photo to the specified path
            image.transferTo(tempFile);
            System.out.println("Uploaded file path: " + imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save uploaded file.", e);
        }

        // Read the uploaded photo using OpenCV
        Mat uploadedImg = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
        if (uploadedImg.empty()) {
            throw new RuntimeException("Failed to read the uploaded image.");
        }

        // Get all photos from the database
        List<PatientMemory> allMemories = patientMemoryService.getAllMemories();
        List<PatientMemory> matchingMemories = new ArrayList<>();

        // SIFT Detector
        SIFT sift = SIFT.create();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();

        // Detect key points and compute descriptors for the uploaded photo
        sift.detectAndCompute(uploadedImg, new Mat(), keypoints1, descriptors1);
        System.out.println("Uploaded photo keypoints: " + keypoints1.toList().size());

        // FLANN-Based Matcher
        FlannBasedMatcher flannMatcher = new FlannBasedMatcher();

        // Match the uploaded photo with each photo in the database
        for (PatientMemory memory : allMemories) {
            Mat img = Imgcodecs.imread(memory.getImage_path(), Imgcodecs.IMREAD_GRAYSCALE);

            if (!img.empty()) {
                MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
                Mat descriptors2 = new Mat();

                // Detect key points and compute descriptors for the database photo
                sift.detectAndCompute(img, new Mat(), keypoints2, descriptors2);
                System.out.println("Database photo keypoints: " + keypoints2.toList().size());

                // Match descriptors
                List<MatOfDMatch> matchesList = new ArrayList<>();
                flannMatcher.knnMatch(descriptors1, descriptors2, matchesList, 2);

                // Filter good matches
                List<DMatch> goodMatches = new ArrayList<>();
                float ratioThresh = 0.75f;
                for (MatOfDMatch matofDMatch : matchesList) {
                    List<DMatch> matches = matofDMatch.toList();
                    if (matches.size() >= 2) {
                        DMatch m1 = matches.get(0);
                        DMatch m2 = matches.get(1);
                        if (m1.distance < ratioThresh * m2.distance) {
                            goodMatches.add(m1);
                        }
                    }
                }

                // Calculate similarity
                double similarity = (goodMatches.size() / (double) keypoints1.toList().size()) * 100;
                System.out.println("Similarity with memory " + memory.getPm_id() + ": " + similarity + "%");

                // If similarity is above 90%, add to matchingMemories
                if (similarity >= 90) {
                    matchingMemories.add(memory);
                }
            }
        }

        return matchingMemories;
    }

}
