package me.kopz.starseeker.entity.dto;

public record LoginResponseDTO(String accessToken, Long expiresIn, Long id) {
}
