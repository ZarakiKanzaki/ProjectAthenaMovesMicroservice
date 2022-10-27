package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.Condition;
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
