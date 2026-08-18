package me.kopz.starseeker.mapper;

import me.kopz.starseeker.entity.Contract;
import me.kopz.starseeker.entity.dto.ContractDTO;
import me.kopz.starseeker.entity.dto.ContractResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {

  public ContractDTO toDTO(Contract contract) {
    return new ContractDTO(
        contract.getId(),
        contract.getArtist().getId(),
        contract.getUser().getId()
    );
  }

  public ContractResponseDTO toResponseDTO(Contract contract) {
    return new ContractResponseDTO(
        contract.getId(),
        contract.getArtist().getId(),
        contract.getArtist().getName(),
        contract.getArtist().getImageUrl(),
        contract.getUser().getId()
    );
  }
}
