package me.kopz.starseeker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Contract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "artist_id")
  @JsonBackReference
  private Artist artist;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private Users user;

  public Contract() {
  }

  public Contract(Long id, Artist artist, Users user) {
    this.id = id;
    this.artist = artist;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
