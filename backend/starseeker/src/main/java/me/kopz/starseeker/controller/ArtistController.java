package me.kopz.starseeker.controller;

import me.kopz.starseeker.entity.SpotifySearchResponse;
import me.kopz.starseeker.service.SpotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {

  private final SpotifyService spotifyService;

  public ArtistController(SpotifyService service){
   this.spotifyService = service;
  }

  @GetMapping("/search")
  public List<SpotifySearchResponse> search(@RequestParam String name){
    return spotifyService.getAllArtists(name);
  }
}
