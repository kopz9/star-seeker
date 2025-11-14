package me.kopz.starseeker.repository;

import me.kopz.starseeker.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
