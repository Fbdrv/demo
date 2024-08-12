package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSaveDto {
    @NotBlank(message = "field.must.be.not.blank")
    private String name;
    @Pattern(regexp = "^[\\p{IsAlphabetic}0-9_.+-]{2,}+@[\\p{IsAlphabetic}0-9_-]{2,}\\.[\\p{IsAlphabetic}0-9_-]{2,}$", message = "invalid.email.format")
    private String email;
    private LocalDate dob;
}
