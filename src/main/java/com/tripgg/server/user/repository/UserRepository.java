package com.tripgg.server.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripgg.server.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  User findByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsById(UUID id);

  Boolean existsByUsername(String username);

}