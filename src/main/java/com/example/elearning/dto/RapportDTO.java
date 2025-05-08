package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapportDTO {
    private Long id;
    private String titre;
    private String text;
    private int estArchive;
    private Date date;

    // Ajout de l'ID de l'enseignant
    private Long enseignantId;
    private String enseignantNom; // Pour afficher le nom de l'enseignant dans l'interface (correction du camelCase)

    // Ajout des IDs des étudiants concernés
    private List<Long> etudiantIds; // Correction du nom (pluriel + camelCase)
    private List<String> etudiantNoms; // Correction du nom (pluriel + camelCase)
}
