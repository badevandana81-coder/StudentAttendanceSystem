package com.attendance.model;
import jakarta.persistence.*;
@Entity
public class Attendance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public Long studentId; public Long subjectId; public String date; public Boolean present;
}
