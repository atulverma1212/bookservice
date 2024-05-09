package com.group32.bookservice.repository;

import com.group32.bookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByusername(String username);

    Optional<User> findByusername(String username);

}
