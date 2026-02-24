package net.engineeringdigest.journaling.services;

import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.repository.userEntrypository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class userEntryServices {



    @Autowired
    private userEntrypository userEntrypository;

    public void  saveEntry(user journalentry){
        userEntrypository.save(journalentry);


    }
    public List<user> getall(){
            return userEntrypository.findAll();     // note we have not mentioned any method yet in journalEnterrepository still it is present due to mongo section that we have included, that provide this feature.

    }
    public Optional<user> findby(ObjectId id){
        return userEntrypository.findById(id);

    }

    public void deletebyid(ObjectId id){
        userEntrypository.deleteById(id);
    }

    public user findbyusername(String username){
        return userEntrypository.findByUsername(username);
    }


}
