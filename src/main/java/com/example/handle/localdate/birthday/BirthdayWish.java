package com.example.handle.localdate.birthday;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BirthdayWish {
    private String recipient;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthDay;
}
