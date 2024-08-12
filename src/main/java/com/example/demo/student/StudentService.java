package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(String.format
                    ("Student with id %d does not exist", studentId));
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long id, String newName, String newEmail) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(String.format
                    ("Student with id %d does not exist", id));
        }
        Student student = studentRepository.findById(id).get();
        if (newName != null  && newName.length() > 0) {
            student.setName(newName);
        }
        if (newEmail != null && newEmail.length() > 0) {
            student.setEmail(newEmail);
        }
    }
}
