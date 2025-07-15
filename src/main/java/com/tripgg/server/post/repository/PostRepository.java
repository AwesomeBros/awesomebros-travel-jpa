package com.tripgg.server.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripgg.server.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
