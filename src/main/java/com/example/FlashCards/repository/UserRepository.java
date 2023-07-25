package com.example.FlashCards.repository;

import com.example.FlashCards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE username COLLATE Latin1_General_BIN = :username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);


}
