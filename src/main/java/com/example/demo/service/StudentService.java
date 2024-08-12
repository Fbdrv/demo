package com.example.demo.service;

import com.example.demo.dto.StudentSaveDto;
import com.example.demo.entity.Student;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(StudentSaveDto dto) {
        Optional<Student> studentOpt = studentRepository.findByEmail(dto.getEmail());
        if (studentOpt.isPresent()) {
            throw new BaseException("Email is taken", HttpStatus.BAD_REQUEST, new ArrayList<>());
        }

        Student student = new Student(dto.getName(), dto.getEmail(), dto.getDob());
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new BaseException(String.format("Student with id %d does not exist", studentId), HttpStatus.BAD_REQUEST, new ArrayList<>());
        }
        studentRepository.deleteById(studentId);
    }

    public void updateStudent(Long id, String newName, String newEmail) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            throw new BaseException(String.format("Student with id %d does not exist", id), HttpStatus.BAD_REQUEST, new ArrayList<>());
        }

        Student student = studentOpt.get();
        if (StringUtils.hasLength(student.getName())) {
            student.setName(newName);
        }
        if (StringUtils.hasLength(student.getEmail())) {
            student.setEmail(newEmail);
        }
        studentRepository.save(student);
    }
}
