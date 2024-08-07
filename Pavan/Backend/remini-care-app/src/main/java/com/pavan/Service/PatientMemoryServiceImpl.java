package com.pavan.Service;

import com.pavan.Entity.PatientMemory;
import com.pavan.Exception.CustomException;
import com.pavan.Repository.PatientMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMemoryServiceImpl implements PatientMemoryService {

    @Autowired
    private PatientMemoryRepository patientMemoryRepository;

    @Override
    public PatientMemory saveMemory(PatientMemory patientMemory) {
        try {
            return patientMemoryRepository.save(patientMemory);
        } catch (DataIntegrityViolationException | IllegalArgumentException exception) {
            throw new CustomException("Invalid memory data: " + exception.getMessage());
        } catch (Exception exception) {
            throw new CustomException("An unexpected error occurred while saving the memory: " + exception.getMessage());
        }
    }

    @Override
    public List<PatientMemory> getAllMemories() {
        return patientMemoryRepository.findAll();
    }
}
