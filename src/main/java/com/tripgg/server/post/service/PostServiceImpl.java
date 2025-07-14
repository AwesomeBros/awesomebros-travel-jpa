package com.tripgg.server.post.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tripgg.server.city.entity.City;
import com.tripgg.server.city.repository.CityRepository;
import com.tripgg.server.country.entity.Country;
import com.tripgg.server.country.repository.CountryRepository;
import com.tripgg.server.district.entity.District;
import com.tripgg.server.district.repository.DistrictRepository;
import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.exception.ApiException;
import com.tripgg.server.image.service.ImageService;
import com.tripgg.server.location.entity.Location;
import com.tripgg.server.location.repository.LocationRepository;
import com.tripgg.server.location.request.LocationRequest;
import com.tripgg.server.post.entity.Post;
import com.tripgg.server.post.repository.PostQueryRepository;
import com.tripgg.server.post.repository.PostRepository;
import com.tripgg.server.post.request.PostRequest;
import com.tripgg.server.post.response.PostResponse;
import com.tripgg.server.user.entity.User;
import com.tripgg.server.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
  private final PostRepository postRepository;
  private final PostQueryRepository postQueryRepository;
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;
  private final DistrictRepository districtRepository;
  private final UserRepository userRepository;
  private final ImageService imageService;
  private final LocationRepository locationRepository;

  /**
   * 메인 인기 & 최신 게시물 조회
   * 
   * @param sort 정렬 기준 (popular, latest)
   * @return 인기 & 최신 게시물 목록 응답 (List<PostResponse>)
   */
  @Override
  public List<PostResponse> findPostsAllBySort(String sort) {
    List<Post> posts = postQueryRepository.findAllBySort(sort);
    List<PostResponse> response = posts.stream()
        .map(PostResponse::from)
        .toList();
    return response;
  }

  /**
   * 메인 도시별 게시물 조회
   * 
   * @param city 도시 기준 (city name)
   * @return 도시별 게시물 목록 응답 (List<PostResponse>)
   */
  @Override
  public List<PostResponse> findPostsAllByCity(String city) {
    List<Post> posts = postQueryRepository.findAllByCity(city);
    List<PostResponse> response = posts.stream()
        .map(PostResponse::from)
        .toList();
    return response;
  }

  /**
   * 게시물 등록
   * 
   * @param PostRequest, user
   * @return 게시물 응답 (PostResponse)
   */
  @Override
  @Transactional
  public PostResponse createPost(PostRequest request, UUID userId) {
    Country country = countryRepository.findById(request.getCountryId())
        .orElseThrow(() -> new ApiException(ErrorCode.COUNTRY_NOT_FOUND));
    City city = cityRepository.findById(request.getCityId())
        .orElseThrow(() -> new ApiException(ErrorCode.CITY_NOT_FOUND));
    District district = districtRepository.findById(request.getDistrictId())
        .orElseThrow(() -> new ApiException(ErrorCode.DISTRICT_NOT_FOUND));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Post post = request.toEntity(city, country, district, user);
    Post savedPost = postRepository.save(post);
    if (request.getUrl() != null) {
      String[] imageArr = { request.getUrl() };
      List<String> finalImageUrls = imageService.manageEntityImages(
          imageArr,
          "posts",
          String.valueOf(savedPost.getId()),
          null);
      request.setUrl(finalImageUrls.get(0));
    }
    if (request.getLocations() != null) {
      for (LocationRequest locationRequest : request.getLocations()) {
        // LOGGER.info("Saving location: {}", locationRequest);
        Location newLocation = Location.from(locationRequest, savedPost);
        savedPost.addLocation(newLocation);
        locationRepository.save(newLocation);
        // LOGGER.info("Location saved: {}", newLocation);
      }
    }
    savedPost.addThumbnail(request.getUrl());
    return PostResponse.from(savedPost);
  }
}