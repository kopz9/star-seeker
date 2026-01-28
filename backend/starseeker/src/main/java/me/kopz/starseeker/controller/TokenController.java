package me.kopz.starseeker.controller;

import me.kopz.starseeker.entity.dto.LoginRequestDTO;
import me.kopz.starseeker.entity.dto.LoginResponseDTO;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class TokenController {

  private final JwtEncoder jwtEncoder;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
    this.jwtEncoder = jwtEncoder;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws AppException{
    var user = userRepository.findByUsername(loginRequestDTO.username());

    if(user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, bCryptPasswordEncoder)){
      throw new AppException("Email or password is invalid", HttpStatus.BAD_REQUEST);
    }

    var expiresIn = 3600L;
    var now = Instant.now();

    var scopes = user.get().getRoles()
        .stream()
        .map(role -> role.getName().toUpperCase())
        .collect(Collectors.joining(" "));

    var claims = JwtClaimsSet.builder()
        .issuer("mybackend")
        .subject(user.get().getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiresIn))
        .claim("scope", scopes)
        .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn, user.get().getId()));
  }
}