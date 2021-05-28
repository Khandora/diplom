package org.khandora.mit.controller;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.dto.UserDto;
import org.khandora.mit.model.User;
import org.khandora.mit.model.UserRole;
import org.khandora.mit.model.enums.Role;
import org.khandora.mit.repository.DiaryRepository;
import org.khandora.mit.repository.TaskRepository;
import org.khandora.mit.repository.UserRepository;
import org.khandora.mit.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DiaryRepository diaryRepository;
    private final TaskRepository taskRepository;

    @GetMapping
    public List<User> getAllTeachers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Error user with id "+ userId +" not found"));
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String strRoles = userDto.getRole();
        Set<UserRole> roles = new HashSet<>();
        switch (strRoles) {
            case "ROLE_ADMIN":
                UserRole adminRole = userRoleRepository.findByRole(Role.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
            case "ROLE_MODERATOR":
                UserRole modRole = userRoleRepository.findByRole(Role.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));roles.add(modRole);
            break;
            default:
                UserRole userRole = userRoleRepository.findByRole(Role.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));roles.add(userRole);
        }

        User user = modelMapper.map(userDto, User.class);;
        user.setUserRoles(roles);
        return userRepository.save(user);
    }

    @PostMapping("/{id}")
    public User editUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody UserDto userDto) {
        User rpUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Error user with id "+ userId +" not found"));
        rpUser.setFirstName(userDto.getFirstName());
        rpUser.setLastName(userDto.getLastName());
        rpUser.setStatus(userDto.getStatus());

        return userRepository.save(rpUser);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable (value = "id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Error user with id "+ userId +" not found" ));

        taskRepository.deleteByUser(user);
        diaryRepository.deleteByScholar(user);
        userRepository.delete(user);
        return ResponseEntity.ok().build();

    }
}
