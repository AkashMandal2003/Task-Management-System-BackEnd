package com.akash.Task_Management_System.repository;

import com.akash.Task_Management_System.entity.User;
import com.akash.Task_Management_System.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole userRole);
}


