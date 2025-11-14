package me.kopz.starseeker.service;

import jakarta.transaction.Transactional;
import me.kopz.starseeker.entity.Artist;
import me.kopz.starseeker.entity.Contract;
import me.kopz.starseeker.entity.Users;
import me.kopz.starseeker.entity.dto.ContractDTO;
import me.kopz.starseeker.exception.AppException;
import me.kopz.starseeker.mapper.ContractMapper;
import me.kopz.starseeker.repository.ArtistRepository;
import me.kopz.starseeker.repository.ContractRepository;
import me.kopz.starseeker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

  private final UserRepository userRepository;
  private final ContractRepository contractRepository;
  private final ArtistRepository artistRepository;
  private final ContractMapper mapper;

  public ContractService(UserRepository userRepository, ContractRepository contractRepository, ArtistRepository artistRepository, ContractMapper mapper) {
    this.userRepository = userRepository;
    this.contractRepository = contractRepository;
    this.artistRepository = artistRepository;
    this.mapper = mapper;
  }


  private void checkAuthorization(Long tokenId, Long userId) throws AppException {
    if (!tokenId.equals(userId)) {
      throw new AppException("You are not authorized to access this task.", HttpStatus.FORBIDDEN);
    }
  }

  private Long getTokenId(JwtAuthenticationToken token) {
    return Long.parseLong(token.getName());
  }

  private Users getAuthenticatedUser(Long id) throws AppException {
    return userRepository.findById(id).orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
  }

  private Artist getArtist(Long id) throws AppException {
    return artistRepository.findById(id).orElseThrow(() -> new AppException("Artist not found", HttpStatus.NOT_FOUND));
  }

  @Transactional
  public ContractDTO hireArtist(JwtAuthenticationToken token, ContractDTO contractDTO) throws AppException {

    Long tokenId = getTokenId(token);
    Users user = getAuthenticatedUser(tokenId);

    checkAuthorization(tokenId, user.getId());

    Artist artist = getArtist(contractDTO.artistId());

    var contract = new Contract();
    contract.setUser(user);
    contract.setArtist(artist);

    Contract saved = contractRepository.save(contract);

    return mapper.toDTO(saved);
  }

  public ContractDTO getContractById(Long id, JwtAuthenticationToken token) throws AppException {

    var contract = contractRepository.findById(id).orElseThrow(() -> new AppException("Contract not found.", HttpStatus.NOT_FOUND));

    Long tokenId = getTokenId(token);
    checkAuthorization(tokenId, contract.getUser().getId());

    return mapper.toDTO(contract);
  }

  @Transactional
  public void deleteContract(Long id, JwtAuthenticationToken token) throws AppException {

    var contract = contractRepository.findById(id).orElseThrow(() -> new AppException("Contract not found.", HttpStatus.NOT_FOUND));

    Long tokenId = getTokenId(token);
    checkAuthorization(tokenId, contract.getUser().getId());

    contractRepository.delete(contract);
  }
}