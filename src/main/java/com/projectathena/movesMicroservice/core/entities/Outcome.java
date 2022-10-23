package com.projectathena.movesMicroservice.core.entities;

import com.projectathena.movesMicroservice.core.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outcome {
    private String description;
    private List<Condition> conditions;
}
