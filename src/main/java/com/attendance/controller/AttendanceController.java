package com.attendance.controller;
import com.attendance.model.*;
import com.attendance.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController @RequestMapping("/api")
public class AttendanceController {
 private final StudentRepository students; private final FacultyRepository faculty;
 private final SubjectRepository subjects; private final AttendanceRepository attendance;
 public AttendanceController(StudentRepository students,FacultyRepository faculty,SubjectRepository subjects,AttendanceRepository attendance){this.students=students;this.faculty=faculty;this.subjects=subjects;this.attendance=attendance;}
 @GetMapping("/students") public List<Student> students(){return students.findAll();}
 @PostMapping("/students") public Student addStudent(@RequestBody Student s){if(s.name==null||s.name.isBlank()||s.rollNumber==null||s.rollNumber.isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name and roll number required");s.id=null;return students.save(s);}
 @DeleteMapping("/students/{id}") public void deleteStudent(@PathVariable Long id){if(!students.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND);attendance.findAll().stream().filter(a->id.equals(a.studentId)).forEach(attendance::delete);students.deleteById(id);}
 @GetMapping("/faculty") public List<Faculty> faculty(){return faculty.findAll();}
 @PostMapping("/faculty") public Faculty addFaculty(@RequestBody Faculty f){if(f.name==null||f.name.isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name required");f.id=null;return faculty.save(f);}
 @DeleteMapping("/faculty/{id}") public void deleteFaculty(@PathVariable Long id){faculty.deleteById(id);}
 @GetMapping("/subjects") public List<Subject> subjects(){return subjects.findAll();}
 @PostMapping("/subjects") public Subject addSubject(@RequestBody Subject s){if(s.code==null||s.code.isBlank()||s.name==null||s.name.isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Code and name required");s.id=null;return subjects.save(s);}
 @DeleteMapping("/subjects/{id}") public void deleteSubject(@PathVariable Long id){if(!subjects.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND);attendance.findAll().stream().filter(a->id.equals(a.subjectId)).forEach(attendance::delete);subjects.deleteById(id);}
 @GetMapping("/attendance") public List<Attendance> attendance(){return attendance.findAll();}
 @PostMapping("/attendance") public Attendance mark(@RequestBody Attendance a){
  if(a.studentId==null||!students.existsById(a.studentId)||a.subjectId==null||!subjects.existsById(a.subjectId)||a.date==null||!a.date.matches("\\d{4}-\\d{2}-\\d{2}")||a.present==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Valid student, subject, date and status required");
  Optional<Attendance> previous=attendance.findAll().stream().filter(x->x.studentId.equals(a.studentId)&&x.subjectId.equals(a.subjectId)&&x.date.equals(a.date)).findFirst();
  a.id=previous.map(x->x.id).orElse(null);return attendance.save(a);
 }
 @GetMapping("/report/{roll}") public Map<String,Object> report(@PathVariable String roll){
  Student s=students.findAll().stream().filter(x->roll.equalsIgnoreCase(x.rollNumber)).findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found"));
  List<Map<String,Object>> rows=new ArrayList<>();List<Attendance> records=attendance.findAll();
  for(Subject subject:subjects.findAll()){
   long total=records.stream().filter(a->s.id.equals(a.studentId)&&subject.id.equals(a.subjectId)).count();
   long present=records.stream().filter(a->s.id.equals(a.studentId)&&subject.id.equals(a.subjectId)&&Boolean.TRUE.equals(a.present)).count();
   rows.add(Map.of("subject",subject.name,"code",subject.code,"total",total,"present",present,"percentage",total==0?0:Math.round(100.0*present/total)));
  }
  return Map.of("student",s,"subjects",rows);
 }
}
