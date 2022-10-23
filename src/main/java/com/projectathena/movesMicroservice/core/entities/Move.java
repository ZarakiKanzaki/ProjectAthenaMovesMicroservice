package com.projectathena.movesMicroservice.core.entities;

import com.projectathena.movesMicroservice.core.enums.MoveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move {
    private long Id;
    private String name;
    private MoveType type;
    private String description;
    private List<String> exampleOfApplication;
    private List<Outcome> outcomes;
}
