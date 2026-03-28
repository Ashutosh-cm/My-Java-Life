package net.engineeringdigest.journaling.controller;

import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.services.userEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userentrycontrollerv2 {

    @Autowired
    private userEntryServices userEntryServices;

    @GetMapping
    public List<user> getallusers(){
        return userEntryServices.getall();

    }


    @PutMapping
    public ResponseEntity<?> updateuser(@RequestBody user user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user userinDB = userEntryServices.findbyusername(username);
        if(userinDB!=null){
            userinDB.setUsername(user.getUsername());
            userinDB.setPassword(user.getPassword());
            userEntryServices.saveNewEntry(userinDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("{username}")
    public ResponseEntity<?> getspecificuser(@PathVariable String username){
        user user = userEntryServices.findbyusername(username);
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("/{id}")
    public boolean DeleteUser(@PathVariable ObjectId id){
        userEntryServices.deletebyid(id);
        return true;

    }



}
