package com.example.elearning.dto;

import com.example.elearning.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class EnseignantDTO extends User {
    private String biographie;
    private boolean status;
    private String specialite;
    private String anneesExperience;
}
