package org.khandora.mit.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.khandora.mit.model.enums.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "userRoles")
@Entity
@Table(
        name = "c_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_name_unique",
                        columnNames = "user_name_")
        }
)
public class User extends BaseModel {

    @Column(name = "email_")
    private String email;

    @Column(name = "user_name_")
    private String userName;

    @Column(name = "password_")
    private String password;

    @Column(name = "first_name_")
    private String firstName;

    @Column(name = "last_name_")
    private String lastName;

    @Column(name = "phone_")
    private Integer phone;

    @Column(name = "age_")
    private Integer age;

    @Column(name = "gender_")
    private String gender;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_")
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "w_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles = new HashSet<>();
}
