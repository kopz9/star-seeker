package me.kopz.starseeker.entity.dto;

public record ContractResponseDTO(
    Long id,
    Long artistId,
    String artistName,
    String artistImageUrl,
    Long userId
) {

}
