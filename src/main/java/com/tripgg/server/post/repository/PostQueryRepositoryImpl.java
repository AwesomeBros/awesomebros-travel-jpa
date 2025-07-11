package com.tripgg.server.post.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripgg.server.district.entity.QDistrict;
import com.tripgg.server.post.entity.Post;
import com.tripgg.server.post.entity.QPost;
import com.tripgg.server.user.entity.QUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
  private final JPAQueryFactory queryFactory;

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
}
