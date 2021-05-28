package org.khandora.mit.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.khandora.mit.model.enums.Level;
import org.khandora.mit.model.enums.Subject;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class DiaryDto implements Serializable {
    private Subject subject;
    private Level level;
    private Integer grade;
}
