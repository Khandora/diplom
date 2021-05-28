package org.khandora.mit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class FileDto implements Serializable {
    private String id;
    private String name;
    private String type;
    private long size;
}
