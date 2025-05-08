package com.example.elearning.dto;

import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ChapitreDTO {
    private Long id;
    private String resumer;
    private String titre;
    private String type;
    @Lob
    private byte[] contenu;
    private Long leconId;
}
