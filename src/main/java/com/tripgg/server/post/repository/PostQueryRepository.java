package com.tripgg.server.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tripgg.server.post.entity.Post;

public interface PostQueryRepository {

  /**
   * 전체 & 국가별 & 도시별 & 지역별 게시물 조회
   * 
   * @param countryName  국가 이름
   * @param cityName     도시 이름
   * @param districtName 지역 이름
   * @param pageable     페이지 정보
   * @return 전체 & 국가별 & 도시별 & 지역별 게시물 목록
   */
  Page<Post> findPostsAll(String countryName, String cityName, String districtName, Pageable pageable);

  /**
   * 전체 & 국가별 & 도시별 & 지역별 게시물 조회
   * 
   * @param countryName  국가 이름
   * @param cityName     도시 이름
   * @param districtName 지역 이름
   * @return 전체 & 국가별 & 도시별 & 지역별 게시물 목록의 총 개수
   */
  int totalCount(String countryName, String cityName, String districtName);

  /**
   * 인기 & 최신 게시물 조회
   * 
   * @param sort 정렬 기준 (popular, latest)
   * @return 인기 & 최신 게시물 목록
   */
  List<Post> findAllBySort(String sort);

  /**
   * 도시별 게시물 조회
   * 
   * @param city 도시 이름
   * @return 도시별 게시물 목록
   */
  List<Post> findAllByCity(String city);
}
