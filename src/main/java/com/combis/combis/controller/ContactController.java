package com.combis.combis.controller;

import com.combis.combis.model.Contact;
import com.combis.combis.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Contact>> findAll() {

        List<Contact> contacts = contactService.findAll();

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<Page<Contact>> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        if (page < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Page cannot be less than 0"));

        Page<Contact> contacts = contactService.findAll(PageRequest.of(page,size));

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact existingContact = contactService.findByEmail(contact.getEmail());

        if (existingContact != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Contact with email %s already exist", contact.getEmail()));

        if (!contactService.isContactInformationsValid(contact))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Contact informations arent valid"));

        String alias = UUID.randomUUID().toString();
        contact.setAlias(alias);
        contactService.createContact(contact);
        logger.info("New contact created {}", contact.getAlias());
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
