package me.kopz.starseeker.controller;

import jakarta.transaction.Transactional;
import me.kopz.starseeker.entity.dto.UserDTO;
import me.kopz.starseeker.entity.dto.UserResponseDTO;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.repository.UserRepository;
import me.kopz.starseeker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @Transactional
  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) throws AppException {
    UserResponseDTO user = userService.createUser(userDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws AppException {
    userRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
