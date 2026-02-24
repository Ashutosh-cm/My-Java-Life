package net.engineeringdigest.journaling.controller;

import net.engineeringdigest.journaling.entity.JournalEntry;
import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.services.journalEntryServices;
import net.engineeringdigest.journaling.services.userEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {

    @Autowired
    private journalEntryServices journalEntryServices;// instance is created here of using auto wire

    @Autowired
    private userEntryServices userEntryServices;



    @GetMapping("/{username}")
    public ResponseEntity<?> getalljournalentryofuser(@PathVariable String username){
        user user = userEntryServices.findbyusername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createentry(@RequestBody JournalEntry myentry, @PathVariable String username){
        try{
            journalEntryServices.saveEntry(myentry,username);

            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(myentry,HttpStatus.NOT_MODIFIED);

        }

    }
    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> myjournalentry(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalentry = journalEntryServices.findby(myid);
        if(journalentry.isPresent()){
            return new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }
    @DeleteMapping ("id/{username}/{myid}")
    public ResponseEntity<JournalEntry> deletemyjournalentry(@PathVariable ObjectId myid, @PathVariable String username){
        journalEntryServices.deletebyid(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<JournalEntry> updatejournalbyid(@PathVariable ObjectId id, @RequestBody JournalEntry newentry, @PathVariable String username ){
        JournalEntry old= journalEntryServices.findby(id).orElse(null);
        if(old!=null){
            if(newentry.getTitle()!=null && !newentry.getTitle().isEmpty()){
                old.setTitle(newentry.getTitle());
            }
            if(newentry.getContent()!=null && !newentry.getContent().isEmpty()){
                old.setContent(newentry.getContent());

            }
            journalEntryServices.saveEntry(old);

            return new ResponseEntity<>(old,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);




    }

}
