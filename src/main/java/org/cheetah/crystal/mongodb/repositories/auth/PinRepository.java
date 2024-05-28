package org.cheetah.crystal.mongodb.repositories.auth;
import java.util.Optional;

import org.cheetah.crystal.dtos.auth.Pin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PinRepository extends MongoRepository<Pin, String> {
    Optional<Pin> findByPin(String pin);
    void deleteByUserId(String userId);
}