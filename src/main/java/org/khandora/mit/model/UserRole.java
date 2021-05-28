package org.khandora.mit.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.khandora.mit.model.enums.Role;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "c_user_role")
public class UserRole extends BaseModel {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_")
    private Role role;
}
