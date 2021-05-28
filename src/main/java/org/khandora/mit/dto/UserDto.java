package org.khandora.mit.dto;

import lombok.*;
import org.khandora.mit.model.enums.Status;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private Integer phone;
    private Integer age;
    @Size(max = 25, min = 3)
    private String email;
    @Size(max = 25, min = 3)
    private String firstName;
    private String gender;
    @Size(max = 25, min = 3)
    private String lastName;
    @NonNull
    @Size(max = 25, min = 3)
    private String userName;
    private String password;
    @NonNull
    private Status status;
    private String role;
}
