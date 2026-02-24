package net.engineeringdigest.journaling.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journaling.entity.JournalEntry;
import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class journalEntryServices {

    @Autowired
    private JournalRepository JournalRepository;
    @Autowired
    private userEntryServices  userEntryServices;



    @Transactional
    public void  saveEntry(JournalEntry journalentry, String username){
        try{
            user user = userEntryServices.findbyusername(username);
            journalentry.setDate(LocalDateTime.now());
            JournalEntry saved = JournalRepository.save(journalentry);// this will save the journal entry

            user.getJournalentries().add(saved);

            userEntryServices.saveEntry(user); // again you have to save this in userservices ,after saving journal in it


        }catch(Exception e){
            throw new RuntimeException("error occured",e);

        }

    }


    // overload save function for put function with change in arguements
    public void  saveEntry(JournalEntry journalentry){
        JournalRepository.save(journalentry);


    }
    public List<JournalEntry> getall(){
            return JournalRepository.findAll();     // note we have not mentioned any method yet in journalEnterrepository still it is present due to mongo section that we have included, that provide this feature.

    }
    public Optional<JournalEntry> findby(ObjectId id){
        return JournalRepository.findById(id);

    }

    public void deletebyid(ObjectId id, String username){
        user deluser = userEntryServices.findbyusername(username);
        deluser.getJournalentries().removeIf(x ->x.getId().equals(id));
        userEntryServices.saveEntry(deluser);
        JournalRepository.deleteById(id);
    }


}
