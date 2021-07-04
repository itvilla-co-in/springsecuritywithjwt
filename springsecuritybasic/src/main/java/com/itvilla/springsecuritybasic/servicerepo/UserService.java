package com.itvilla.springsecuritybasic.servicerepo;


import com.itvilla.springsecuritybasic.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
 import java.util.Date;
 import static com.itvilla.springsecuritybasic.model.Constants.*;
import static com.itvilla.springsecuritybasic.model.Role.*;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {
        System.out.println("coming in regiser service ");
   /*     User userByNewUsername = userRepository.findUserByUsername(username);
        User userByNewEmail = userRepository.findUserByEmail(email);
        if(userByNewUsername != null) {
            throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }
        if(userByNewEmail != null) {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }*/
        User user = new User();
        user.setUserId(RandomStringUtils.randomNumeric(10));
        String password = RandomStringUtils.randomAlphanumeric(10);;
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_ADMIN.name());
        user.setAuthorities(ROLE_ADMIN.getAuthorities());
        System.out.println(user.getFirstName() + user.getLastName() + user.getPassword());
         userRepository.save(user);
        LOGGER.info("New user password: " + password);
        return user;
    }

}
