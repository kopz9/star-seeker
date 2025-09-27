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
  private User user;


}
