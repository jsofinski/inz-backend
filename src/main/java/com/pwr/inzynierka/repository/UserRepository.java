package com.pwr.inzynierka.repository;

import com.pwr.inzynierka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPublicKey(String publicKey);

    Optional<User> findByName(String name);
}
