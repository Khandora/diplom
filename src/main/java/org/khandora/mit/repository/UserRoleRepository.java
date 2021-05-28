package org.khandora.mit.repository;

import org.khandora.mit.model.UserRole;
import org.khandora.mit.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(Role name);
}
