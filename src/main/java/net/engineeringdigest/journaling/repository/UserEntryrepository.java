package net.engineeringdigest.journaling.repository;

import net.engineeringdigest.journaling.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryrepository extends MongoRepository<user, ObjectId> {
    user findByUsername(String username);
    void deleteByUsername(String username);
}
