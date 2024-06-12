package com.example.DAJava.repository;

import com.example.DAJava.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
}
