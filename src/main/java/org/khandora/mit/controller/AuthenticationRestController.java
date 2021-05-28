package org.khandora.mit.controller;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.dto.JwtResponse;
import org.khandora.mit.dto.UserDto;
import org.khandora.mit.model.User;
import org.khandora.mit.model.UserRole;
import org.khandora.mit.model.enums.Role;
import org.khandora.mit.repository.UserRepository;
import org.khandora.mit.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
            User user = userRepository.findByUserName(userDto.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
            String token = jwtTokenProvider.createToken(userDto.getUserName(), user.getUserRoles());

            Set<Role> roleSet = user.getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toSet());
            return ResponseEntity.ok(new JwtResponse(
                    token,
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    roleSet));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username/password combination || user was banned", HttpStatus.FORBIDDEN);
        }
    }
}
