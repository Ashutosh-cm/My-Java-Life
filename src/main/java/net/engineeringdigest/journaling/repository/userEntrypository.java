package net.engineeringdigest.journaling.repository;

import net.engineeringdigest.journaling.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userEntrypository extends MongoRepository<user, ObjectId> {
    user findByUsername(String username);
}
