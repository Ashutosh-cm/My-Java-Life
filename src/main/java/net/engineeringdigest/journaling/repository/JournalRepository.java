package net.engineeringdigest.journaling.repository;

import net.engineeringdigest.journaling.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalRepository extends MongoRepository<JournalEntry,ObjectId>{
}



//public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {
//}
