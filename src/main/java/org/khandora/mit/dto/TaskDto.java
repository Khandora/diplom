package org.khandora.mit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.khandora.mit.model.enums.Level;
import org.khandora.mit.model.enums.Subject;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {
    private Long id;
    private Level level;
    private String name;
    private String question;
    private Subject subject;
}
