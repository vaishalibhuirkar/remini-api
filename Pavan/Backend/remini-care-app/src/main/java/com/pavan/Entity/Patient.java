package com.pavan.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "patient_details")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int p_id;

    private String name;

    private String disorders;

    private int age;

    private String address;

    private String pincode;

    public Patient() {
    }

    public Patient(int p_id, String name, String disorders, int age, String address, String pincode) {
        this.p_id = p_id;
        this.name = name;
        this.disorders = disorders;
        this.age = age;
        this.address = address;
        this.pincode = pincode;
    }
}
