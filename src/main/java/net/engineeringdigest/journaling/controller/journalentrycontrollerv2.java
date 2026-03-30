package net.engineeringdigest.journaling.controller;

import net.engineeringdigest.journaling.entity.JournalEntry;
import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.services.journalEntryServices;
import net.engineeringdigest.journaling.services.userEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {

    @Autowired
    private journalEntryServices journalEntryServices;// instance is created here of using auto wire

    @Autowired
    private userEntryServices userEntryServices;



    @GetMapping
    public ResponseEntity<?> getalljournalentryofuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user user = userEntryServices.findbyusername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }
    @PostMapping()
    public ResponseEntity<JournalEntry> createentry(@RequestBody JournalEntry myentry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            journalEntryServices.saveEntry(myentry,username);

            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(myentry,HttpStatus.NOT_MODIFIED);

        }

    }
    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> myjournalentry(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user user = userEntryServices.findbyusername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        // this above line we have find the user journal entries and have searcher if that id is present or not
          // in the journal entries.
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalentry = journalEntryServices.findby(myid);
            if(journalentry.isPresent()){
                return new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }

        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }
    @DeleteMapping ("id/{myid}")
    public ResponseEntity<JournalEntry> deletemyjournalentry(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
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
