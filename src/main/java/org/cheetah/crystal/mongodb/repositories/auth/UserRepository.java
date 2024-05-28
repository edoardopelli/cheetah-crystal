package org.cheetah.crystal.mongodb.repositories.auth;
import java.util.Optional;

import org.cheetah.crystal.dtos.auth.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

	Optional<User> findByOtp(String pin);
}