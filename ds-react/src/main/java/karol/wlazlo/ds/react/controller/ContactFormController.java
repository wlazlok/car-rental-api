package karol.wlazlo.ds.react.controller;

import lombok.extern.slf4j.Slf4j;
import karol.wlazlo.model.ContactForm.ContactForm;
import karol.wlazlo.model.ContactForm.ContactFormResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/react")
public class ContactFormController {

    //todo: oblsuga formularza

    @PostMapping("/contact-form")
    public ResponseEntity<ContactFormResponse> receiveContactForm(@RequestBody ContactForm contactForm) {
        System.out.println(contactForm);

        return ResponseEntity.status(HttpStatus.OK).body(ContactFormResponse.builder().message("Wysłano wiadomość").build());
    }
}
