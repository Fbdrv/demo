package com.example.demo.config;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mika = new Student(
                "Mika",
                "Mika@mail.ru",
                LocalDate.of(2005,  MARCH, 7));
            Student mariam = new Student(
                "Mariam",
                "Mariam@mail.ru",
                LocalDate.of(2003,  MARCH, 7));
            repository.saveAll(
                    List.of(mika, mariam)
            );
        };
    }
}
