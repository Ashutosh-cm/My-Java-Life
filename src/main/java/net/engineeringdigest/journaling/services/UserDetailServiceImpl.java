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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user = userEntryrepository.findByUsername(username);
        if(user!=null){
            UserDetails userDetails=org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;

        }
        throw new UsernameNotFoundException("user not found with username:"+ username);

    }
}
