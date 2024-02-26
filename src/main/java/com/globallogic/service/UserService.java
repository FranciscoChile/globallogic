package com.globallogic.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.model.Phone;
import com.globallogic.model.SuccessResponse;
import com.globallogic.model.User;
import com.globallogic.persistence.PhoneRepository;
import com.globallogic.persistence.UserRepository;
import com.globallogic.web.exception.DataValidationException;
import com.globallogic.web.exception.UserNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private PhoneRepository phoneRepository;


    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
    }

    public SuccessResponse create(User user, String token) {    
        SuccessResponse response = new SuccessResponse();
        List<User> userEmail = userRepository.findByEmail(user.getEmail());

        if (userEmail.size() > 0) {
            throw new DataValidationException("El correo se encuentra registrado");
        }

        if (!patternMatchesEmail(user.getEmail())) {
            throw new DataValidationException("El formato del email es incorrecto");
        }

        if (!patternMatchesPassword(user.getPassword())) {
            throw new DataValidationException("El formato del password es incorrecto");
        }

        String jwtToken = token.substring(7);
        user.setToken(jwtToken);
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setActive(true);

        userRepository.save(user);

        Set<Phone> phones = user.getPhones();

        phones.stream().forEach(
            e -> phoneRepository.save(new Phone(e.getNumber(), e.getCityCode(), e.getCountryCode(), user))
            );
        

        response.setId(user.getId());
        response.setCreated(user.getCreated());
        response.setModified(user.getModified());
        response.setLastLogin(user.getLastLogin());
        response.setToken(user.getToken());
        response.setActive(user.isActive());

        return response;

    }

    public static boolean patternMatchesEmail(String emailAddress) {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(regexPattern)
          .matcher(emailAddress)
          .matches();
    }
    
    public static boolean patternMatchesPassword(String password) {

        String regexPattern = "^[A][a-z]*[0-9]{2}";

        return Pattern.compile(regexPattern)
          .matcher(password)
          .matches();
    }
}
