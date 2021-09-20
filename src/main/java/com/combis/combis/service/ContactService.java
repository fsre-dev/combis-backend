package com.combis.combis.service;

import com.combis.combis.model.Contact;
import com.combis.combis.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService {

        @Autowired
        private ContactRepository contactRepository;

        private final String emailRegex= "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        private final String mobileRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

        public List<Contact> findAll() {
        return contactRepository.findAll();
        }

        public Page<Contact> findAll(PageRequest pageRequest) {
            return contactRepository.findAll(pageRequest);
        }

        public Contact findByEmail(String email) {
            return contactRepository.findByEmail(email);
        }

        public Contact createContact(Contact contact) {
            return contactRepository.save(contact);
        }

        public Boolean isContactInformationsValid(Contact contact) {

            return contact.getEmail().matches(emailRegex) && contact.getMobile().matches(mobileRegex);
        }
}
