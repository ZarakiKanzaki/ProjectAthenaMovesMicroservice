package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    private String name;
    private short value;
    private TagType type;
}
