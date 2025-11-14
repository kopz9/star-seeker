package me.kopz.starseeker.config;

import jakarta.transaction.Transactional;
import me.kopz.starseeker.entity.Role;
import me.kopz.starseeker.entity.Users;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.repository.RoleRepository;
import me.kopz.starseeker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    roleRepository.findByName("ADMIN").orElseGet(() -> {
      Role role = new Role();
      role.setName("ADMIN");
      return roleRepository.save(role);
    });

    roleRepository.findByName("BASIC").orElseGet(() -> {
      Role role = new Role();
      role.setName("BASIC");
      return roleRepository.save(role);
    });

    var roleAdmin = roleRepository.findByName("ADMIN").orElseThrow(() -> new AppException("Admin role not found", HttpStatus.NOT_FOUND));
    var userAdmin = userRepository.findByUsername("ADMIN");

    userAdmin.ifPresentOrElse(
        user -> System.out.println("Admin already exists!"),
        () -> {
          var user = new Users();
          user.setUsername("ADMIN");
          user.setEmail("admin@mail.com");
          user.setPassword(bCryptPasswordEncoder.encode("123"));
          user.setRoles(Set.of(roleAdmin));
          userRepository.save(user);
        }
    );
  }

}
