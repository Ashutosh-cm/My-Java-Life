package net.engineeringdigest.journaling.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="journal_enteries")
@Data
@NoArgsConstructor
//@AllArgsConstructor// it is the annotation which consist of all the getter and setters.
//@Getter
//@Setter
public class JournalEntry {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;


}
