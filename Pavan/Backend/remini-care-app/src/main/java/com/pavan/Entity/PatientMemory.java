package com.pavan.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
//@Table(name = "patient_memory")
public class PatientMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pm_id;

    private String memory_desc;

    private Date date;

    private String image_path;
}
