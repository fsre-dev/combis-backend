package com.combis.combis.repository;

import com.combis.combis.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

     List<Contact> findAll();

     Page<Contact> findAll(Pageable pageable);

     Contact findByEmail(String email);

}
