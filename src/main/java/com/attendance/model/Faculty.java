package com.attendance.model;
import jakarta.persistence.*;
@Entity
public class Faculty {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public String name; public String email;
}
