package net.engineeringdigest.journaling.services;

import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.repository.UserEntryrepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class userEntryServices {



    @Autowired
    private UserEntryrepository UserEntryrepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void  saveEntry(user user){
        UserEntryrepository.save(user);


    }
    public void  saveNewEntry(user user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("user"));
        UserEntryrepository.save(user);


    }
    public List<user> getall(){
            return UserEntryrepository.findAll();     // note we have not mentioned any method yet in journalEnterrepository still it is present due to mongo section that we have included, that provide this feature.

    }
    public Optional<user> findby(ObjectId id){
        return UserEntryrepository.findById(id);

    }

    public void deletebyid(ObjectId id){
        UserEntryrepository.deleteById(id);
    }

    public user findbyusername(String username){
        return UserEntryrepository.findByUsername(username);
    }


}
