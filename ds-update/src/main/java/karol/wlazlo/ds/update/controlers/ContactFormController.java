package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.ds.update.services.EmailService;
import karol.wlazlo.model.ContactForm.ContactForm;
import karol.wlazlo.model.ContactForm.ContactFormResponse;
import karol.wlazlo.model.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@RestController
@RequestMapping("/ds/update/contact")
public class ContactFormController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<ContactFormResponse> sendEmail(@RequestBody ContactForm form) {
        ContactFormResponse response = new ContactFormResponse();

        try {
            emailService.sendContactEmail(form);
            response.setMessage("Wiadomość została wysłana");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
