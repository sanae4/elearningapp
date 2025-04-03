package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeçonCreateUpdateDTO {
    private String titreLeçon;
    private String description;

    private int nbrChapitres;
    private String conseilsEnseignant;
    private boolean estSupprimé;

    // Référence au cours associé
    private Long courseId;
    private boolean useAI;

    public boolean isUseAI() {
        return useAI;
    }

    public void setUseAI(boolean useAI) {
        this.useAI = useAI;
    }
}
