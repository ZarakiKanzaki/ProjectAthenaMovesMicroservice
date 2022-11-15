package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.MoveType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Move extends Entity{
    private long id;
    private String name;
    private MoveType type;
    private String description;
    private List<String> exampleOfApplication = new ArrayList<>();
    private List<Outcome> outcomes = new ArrayList<>();
}
