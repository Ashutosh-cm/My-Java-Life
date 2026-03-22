package net.engineeringdigest.journaling.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="users")
@Data          // it is the annotation which consist of all the getter and setters.
//@Getter
//@Setter

public class user{
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;


    @DBRef(lazy = true)
    private List<JournalEntry> JournalEntries =new ArrayList<>();

    private List<String> roles;
}


//public class user {
//    @Id
//    private ObjectId id;
//
//    @Indexed(unique = true)
//    @NonNull
//    private String username;               // there must be unique username that is why indexed has been used
//
//    @NonNull
//    private String password;     // both have them should not be null
//
//    @DBRef(lazy = true)
//    private List<JournalEntry> journalentries=new ArrayList<>();
//
//
//
//}
