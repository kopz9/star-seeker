package me.kopz.starseeker.repository;

import me.kopz.starseeker.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findByUri(String uri);
}
