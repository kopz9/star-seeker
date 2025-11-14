package me.kopz.starseeker.service;

import jakarta.transaction.Transactional;
import me.kopz.starseeker.entity.Role;
import me.kopz.starseeker.entity.Users;
import me.kopz.starseeker.entity.dto.UserDTO;
import me.kopz.starseeker.entity.dto.UserResponseDTO;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.repository.RoleRepository;
import me.kopz.starseeker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Transactional
  public UserResponseDTO createUser(UserDTO dto) throws AppException {
    if (userRepository.findByEmail(dto.email()).isPresent()) {
      throw new AppException("User already exists", HttpStatus.CONFLICT);
    }

    var basicRole = roleRepository.findByName(Role.Values.BASIC.name()).orElseThrow(() -> new AppException("Role not found", HttpStatus.NOT_FOUND));

    Users newUser = new Users();
    newUser.setEmail(dto.email());
    newUser.setUsername(dto.username());
    newUser.setPassword(bCryptPasswordEncoder.encode(dto.password()));
    newUser.setRoles(Set.of(basicRole));

    var saved = userRepository.save(newUser);

    return new UserResponseDTO(saved.getId(), saved.getEmail(), saved.getUsername());
  }

  @Transactional
  public void deleteUser(Long id) throws AppException {
    if (!userRepository.existsById(id)) {
      throw new AppException("User not found", HttpStatus.NOT_FOUND);
    }
    userRepository.deleteById(id);
  }
}