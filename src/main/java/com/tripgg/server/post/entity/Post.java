package com.tripgg.server.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.tripgg.server.city.entity.City;
import com.tripgg.server.country.entity.Country;
import com.tripgg.server.district.entity.District;
import com.tripgg.server.location.entity.Location;
import com.tripgg.server.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  private String content;

  private String slug;

  @Column(name = "view_count")
  private int viewCount;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cities_id")
  private City city;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "districts_id")
  private District district;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "countries_id")
  private Country country;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @Default
  private List<Location> locations = new ArrayList<>();

  public void addThumbnail(String url) {
    if (url != null) {
      this.url = url;
    }
  }

  public void addLocation(Location location) {
    if (location != null) {
      this.locations.add(location);
    }
  }
}
