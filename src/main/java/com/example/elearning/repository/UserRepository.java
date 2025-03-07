package com.example.elearning.repository;

import com.example.elearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email); // Retourne un User ou null si non trouvé
}
