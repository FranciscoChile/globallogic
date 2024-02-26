package com.globallogic.persistence;

import org.springframework.data.repository.CrudRepository;

import com.globallogic.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    
}
