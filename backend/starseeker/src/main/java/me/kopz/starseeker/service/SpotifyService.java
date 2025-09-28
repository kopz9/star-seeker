package me.kopz.starseeker.service;

import me.kopz.starseeker.entity.SpotifySearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Service
public class SpotifyService {

  private final WebClient webClient;

  public SpotifyService() {
    this.webClient = WebClient.builder()
        .baseUrl("https://spotify23.p.rapidapi.com")
        .defaultHeader("X-RapidAPI-Key", "c57ec4b048mshf8d88185a6b44dep132c3cjsn23499f236a2c")
        .defaultHeader("X-RapidAPI-Host", "spotify23.p.rapidapi.com")
        .build();
  }

  public List<SpotifySearchResponse> getAllArtists(String query) {
    JsonNode root = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/search")
            .queryParam("q", query)
            .queryParam("type", "artists")
            .queryParam("offset", 0)
            .queryParam("limit", 12)
            .build())
        .retrieve()
        .bodyToMono(JsonNode.class)
        .block();

    return root.at("/artists/items").findValues("data").stream()
        .map(dataNode -> {
          String name = dataNode.at("/profile/name").asText(null);
          String imageUrl = dataNode.at("/visuals/avatarImage/sources/0/url").asText(null);
          return new SpotifySearchResponse(name, imageUrl);
        })
        .toList();
  }
}
