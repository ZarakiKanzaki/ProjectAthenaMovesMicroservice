package com.projectathena.movesmicroservice.core.entities;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class CharacterStatus {
    private String name;
    private short value;
}
