package com.globallogic.web;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;

import com.globallogic.model.Phone;
import com.globallogic.model.User;
import com.globallogic.persistence.PhoneRepository;
import com.globallogic.persistence.UserRepository;


public class DataLoad implements CommandLineRunner {


    private UserRepository userRepository;
    private PhoneRepository phoneRepository;

    public DataLoad(UserRepository userRepository, PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User("name", "asdasd@asdasd.com", "lalala123");        
        userRepository.save(user);

        phoneRepository.save(new Phone("3300412", "412", "1", user));        

        User user1 = new User("name1", "1asdasd@asdasd.com", "1lalala123");        
        userRepository.save(user1);
  
        phoneRepository.save(new Phone("222222", "222", "2", user1));

        Set<Phone> phones = userRepository.findByName("name").get(0).getPhones();

        phones.stream().forEach(e -> System.out.println(e.getNumber()));

        System.out.println(userRepository.findByName("name").get(0).getId());
        System.out.println(userRepository.findByName("name1").get(0).getId());
        // System.out.println(userRepository.count());

        // System.out.println(userRepository.findByName("name1"));
        // System.out.println(userRepository.count());

    }
    
}
