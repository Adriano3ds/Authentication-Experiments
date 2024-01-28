package io.adriano3ds.AuthenticationExperiments.repositories;

import io.adriano3ds.AuthenticationExperiments.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

}
