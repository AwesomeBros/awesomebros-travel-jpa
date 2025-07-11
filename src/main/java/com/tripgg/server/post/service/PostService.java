package com.tripgg.server.post.service;

import java.util.List;

import com.tripgg.server.post.response.PostResponse;

public interface PostService {

  List<PostResponse> findPostsAllBySort(String sort);

}
