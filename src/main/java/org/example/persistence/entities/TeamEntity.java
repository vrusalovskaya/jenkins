package org.example.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamEntity {
    private Integer id;
    private String name;
}
