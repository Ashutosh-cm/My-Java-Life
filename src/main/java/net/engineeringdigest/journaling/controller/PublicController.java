package net.engineeringdigest.journaling.controller;

import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.services.userEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {
    @Autowired
    private userEntryServices userEntryServices;

    @PostMapping("create_user")
    public void createuserentry(@RequestBody user user){
        userEntryServices.saveNewEntry(user);

    }
}
