package com.example.demo.controller;

import com.example.demo.dto.StudentSaveDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }
    @PostMapping
    public void registerStudent(@RequestBody StudentSaveDto dto) {
        studentService.addNewStudent(dto);
    }

    @DeleteMapping(path = "/{studentId}")
    public void deleteStudent(@PathVariable("studentId") long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "/{studentId}")
    public void updateStudent(
            @PathVariable("studentId") long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}

