package com.attendance.model;
import jakarta.persistence.*;
@Entity
public class Student {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public String rollNumber; public String name; public String department;
}
