
     //     This class connects our database users with Spring Security’s authentication system

package net.engineeringdigest.journaling.services;

import ch.qos.logback.core.util.ReentryGuard;
import net.engineeringdigest.journaling.entity.user;
import net.engineeringdigest.journaling.repository.UserEntryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryrepository userEntryrepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // when ever user try to login this function is called
        user user = userEntryrepository.findByUsername(username);
        if(user!=null){
            UserDetails userDetails=org.springframework.security.core.userdetails.User.builder()  // Then we convert our custom user entity into Spring’s UserDetails object
                    .username(user.getUsername())   // Sets username
                    .password(user.getPassword())   // sets password
                    .roles(user.getRoles().toArray(new String[0]))  // set roles
                    .build();
            return userDetails;  // spring now compares the entered password v/s stored password and role for authorization


        }
        throw new UsernameNotFoundException("user not found with username:"+ username);
        // if the user is not found or password is incorrect then, spring return authorization faliure.

    }

    // real flow
//    1.User enters username/password
//
//    2.Spring Security calls:
//
//         loadUserByUsername(username)
//    3.Your code fetches user from DB
//    4.Returns UserDetails
//    5.Spring verifies password
//    6.If correct → login success

}
