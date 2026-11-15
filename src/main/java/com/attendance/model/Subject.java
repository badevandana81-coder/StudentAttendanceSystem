package com.attendance.model;
import jakarta.persistence.*;
@Entity
public class Subject {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public String code; public String name; public String facultyName;
}
