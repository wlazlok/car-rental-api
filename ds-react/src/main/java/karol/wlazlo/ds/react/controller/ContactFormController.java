package karol.wlazlo.ds.react.controller;

import karol.wlazlo.commons.clients.DSUpdateClient;
import lombok.extern.slf4j.Slf4j;
import karol.wlazlo.model.ContactForm.ContactForm;
import karol.wlazlo.model.ContactForm.ContactFormResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/react")
public class ContactFormController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @PostMapping("/contact-form")
    public ResponseEntity<ContactFormResponse> receiveContactForm(@RequestBody ContactForm contactForm) {
        return dsUpdateClient.sendEmail(contactForm);
    }
}
