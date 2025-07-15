package com.tripgg.server.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripgg.server.city.entity.QCity;
import com.tripgg.server.country.entity.QCountry;
import com.tripgg.server.district.entity.QDistrict;
import com.tripgg.server.post.entity.Post;
import com.tripgg.server.post.entity.QPost;
import com.tripgg.server.user.entity.QUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
  private final JPAQueryFactory queryFactory;

  /**
   * 전체 & 국가별 & 도시별 & 지역별 게시물 조회
   * 
   * @param countryName  국가 이름
   * @param cityName     도시 이름
   * @param districtName 지역 이름
   * @param pageable     페이지 정보
   * @return 전체 & 국가별 & 도시별 & 지역별 게시물 목록
   */
  @Override
  public Page<Post> findPostsAll(String countryName, String cityName, String districtName, Pageable pageable) {
    QPost post = QPost.post;
    QCountry country = QCountry.country;
    QCity city = QCity.city;
    QDistrict district = QDistrict.district;
    QUser user = QUser.user;
    List<Post> content = queryFactory
        .selectFrom(post)
        .leftJoin(post.country, country).fetchJoin()
        .leftJoin(post.city, city).fetchJoin()
        .leftJoin(post.district, district).fetchJoin()
        .leftJoin(post.user, user).fetchJoin()
        .where(
            (countryName != null ? country.name.eq(countryName) : null),
            (cityName != null ? city.name.eq(cityName) : null),
            (districtName != null ? district.name.eq(districtName) : null))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.createdAt.desc())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(post.count())
        .from(post)
        .leftJoin(post.country, country)
        .leftJoin(post.city, city)
        .leftJoin(post.district, district)
        .leftJoin(post.user, user)
        .where(
            (countryName != null ? country.name.eq(countryName) : null),
            (cityName != null ? city.name.eq(cityName) : null),
            (districtName != null ? district.name.eq(districtName) : null));
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  /**
   * 전체 & 국가별 & 도시별 & 지역별 게시물 조회
   * 
   * @param countryName  국가 이름
   * @param cityName     도시 이름
   * @param districtName 지역 이름
   * @return 전체 & 국가별 & 도시별 & 지역별 게시물 목록의 총 개수
   */
  @Override
  public int totalCount(String countryName, String cityName, String districtName) {
    QPost post = QPost.post;
    QCountry country = QCountry.country;
    QCity city = QCity.city;
    QDistrict district = QDistrict.district;
    return Math.toIntExact(queryFactory
        .select(post.count())
        .from(post)
        .leftJoin(post.country, country)
        .leftJoin(post.city, city)
        .leftJoin(post.district, district)
        .where(
            (countryName != null ? country.name.eq(countryName) : null),
            (cityName != null ? city.name.eq(cityName) : null),
            (districtName != null ? district.name.eq(districtName) : null))
        .fetchOne());
  }

  /**
   * 인기 & 최신 게시물 조회
   * 
   * @param sort 정렬 기준 (popular, latest)
   * @return 인기 & 최신 게시물 목록
   */
  @Override
  public List<Post> findAllBySort(String sort) {
    QPost post = QPost.post;
    QUser user = QUser.user;
    QDistrict district = QDistrict.district;
    return queryFactory
        .selectFrom(post)
        .join(post.user, user).fetchJoin()
        .join(post.district, district).fetchJoin()
        .limit(8)
        .orderBy(
            sort.equals("popular") ? post.viewCount.desc() : post.createdAt.desc())
        .fetch();
  }

  /**
   * 도시별 게시물 조회
   * 
   * @param city 도시 이름
   * @return 도시별 게시물 목록
   */
  @Override
  public List<Post> findAllByCity(String city) {
    QPost post = QPost.post;
    QUser user = QUser.user;
    QDistrict district = QDistrict.district;
    return queryFactory
        .selectFrom(post)
        .where(post.city.name.eq(city))
        .join(post.user, user).fetchJoin()
        .join(post.district, district).fetchJoin()
        .limit(8)
        .orderBy(post.createdAt.desc())
        .fetch();
  }
}
