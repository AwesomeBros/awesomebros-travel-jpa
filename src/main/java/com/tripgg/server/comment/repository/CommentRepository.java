package com.tripgg.server.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tripgg.server.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

  Page<Comment> findByPost_Id(int postId, Pageable pageable);

}
