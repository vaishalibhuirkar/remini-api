package com.pavan.Service;

import com.pavan.Entity.PatientMemory;

import java.util.List;

public interface PatientMemoryService {

    PatientMemory saveMemory(PatientMemory patientMemory);

    List<PatientMemory> getAllMemories();

}
