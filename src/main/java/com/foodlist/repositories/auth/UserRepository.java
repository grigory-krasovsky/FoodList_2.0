package com.foodlist.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.foodlist.models.auth.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
