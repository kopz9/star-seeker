package me.kopz.starseeker.controller;

import jakarta.validation.Valid;
import me.kopz.starseeker.entity.dto.ContractDTO;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contracts")
public class ContractController {

  private final ContractService contractService;

  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @PostMapping
  public ResponseEntity<ContractDTO> createContract(@AuthenticationPrincipal JwtAuthenticationToken token,@Valid @RequestBody ContractDTO contractDTO) throws AppException {
    ContractDTO contract = contractService.hireArtist(token, contractDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(contract);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContract(@PathVariable Long id, @AuthenticationPrincipal JwtAuthenticationToken token) throws AppException {
    contractService.deleteContract(id, token);
    return ResponseEntity.noContent().build();
  }
}