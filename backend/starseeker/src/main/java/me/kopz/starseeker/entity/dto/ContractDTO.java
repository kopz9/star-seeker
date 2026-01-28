package me.kopz.starseeker.entity.dto;

import jakarta.validation.constraints.NotNull;

public record ContractDTO(
    Long id,

    @NotNull(message = "Artist ID is required")
    Long artistId,

    Long userId) {
}
