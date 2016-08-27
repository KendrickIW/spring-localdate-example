package com.example.handle.localdate.birthday;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Birthday {
    private String name;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;
}
