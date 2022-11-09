package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.Condition;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Outcome {
    private String description;
    private List<Condition> conditions;
}
