package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.TagType;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class Tag {
    private String name;
    private short value;
    private TagType type;
}
