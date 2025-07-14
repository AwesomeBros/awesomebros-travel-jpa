package com.tripgg.server.location.entity;

import com.tripgg.server.location.request.LocationRequest;
import com.tripgg.server.post.entity.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "locations")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private double lat;
  private double lng;
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "posts_id")
  private Post post;

  public static Location from(LocationRequest request, Post post) {
    return Location.builder()
        .lat(request.getLat())
        .lng(request.getLng())
        .name(request.getName())
        .post(post)
        .build();
  }

}
