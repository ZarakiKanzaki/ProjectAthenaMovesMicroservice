package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.MoveType;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Move implements Entity{
    private String name;
    private MoveType type;
    private String description;
    private List<String> exampleOfApplication;
    private List<Outcome> outcomes;
}
