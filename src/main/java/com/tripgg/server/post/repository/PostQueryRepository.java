package com.tripgg.server.post.repository;

import java.util.List;

import com.tripgg.server.post.entity.Post;

public interface PostQueryRepository {

  List<Post> findAllBySort(String sort);

}
