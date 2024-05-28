package org.cheetah.crystal.dtos.auth;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "pins")
public class Pin {
    @Id
    private String id;

    private String pin;

    private String userId;

    private Instant createdAt;

    public Pin(String pin, String userId) {
        this.pin = pin;
        this.userId = userId;
        this.createdAt = Instant.now();
    }
}