package me.kopz.starseeker.service;

import me.kopz.starseeker.entity.Artist;
import me.kopz.starseeker.entity.SpotifySearchResponse;
import me.kopz.starseeker.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Service
public class SpotifyService {

  private final WebClient webClient;

  private final ArtistRepository artistRepository;

  public SpotifyService(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
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
          String uri = dataNode.at("/uri").asText(null);
          String name = dataNode.at("/profile/name").asText(null);
          String imageUrl = dataNode.at("/visuals/avatarImage/sources/0/url").asText(null);

          Artist artist = findOrCreateArtist(uri, name, imageUrl);
          return new SpotifySearchResponse(artist.getId(), name, imageUrl);
        })
        .toList();
  }

  private Artist findOrCreateArtist(String uri, String name, String imageUrl) {
    return artistRepository.findByUri(uri)
        .orElseGet(() -> {
          var artist = new Artist();
          artist.setUri(uri);
          artist.setName(name);
          artist.setImageUrl(imageUrl);
          return artistRepository.save(artist);
        });
  }
}
