package me.kopz.starseeker.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String uri;
  private String name;
  private String imageUrl;

  @OneToMany(mappedBy = "artist")
  @JsonManagedReference
  private List<Contract> contracts;

  public Artist() {
  }

  public Artist(Long id, String uri, String name, String imageUrl, List<Contract> contracts) {
    this.id = id;
    this.uri = uri;
    this.name = name;
    this.imageUrl = imageUrl;
    this.contracts = contracts;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }
}
