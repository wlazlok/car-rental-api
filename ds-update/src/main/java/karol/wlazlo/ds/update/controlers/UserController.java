package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/ds/update/user")
@RestController
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterForm registerForm) {
        //todo: obsługa błędów, przeniesnie logiki do serwisów,errory po errorCode
        //todo wyslanie mejla z linkiem, zablokowanie konta do czasu aktywowania!

        Response response = new Response();

        Optional<AppUser> user = appUserRepository.findAppUserByEmail(registerForm.getEmail());

        if (user.isPresent()) {
            response.setErrors(List.of(ErrorMessage.builder()
                    .message("Użytkownik o podanym adresie email istnieje")
                    .build()));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setSuccessMessage("Link aktywujący został wysłany na adres e-mail");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm) {
        //todo: obsługa błędów, przeniesnie logiki do serwisów,errory po errorCode
        //todo wyslanie mejla z linkiem, zablokowanie konta do czasu aktywowania!

        Response response = new Response();
        log.info("reset password by email: {}", resetPasswordForm.getEmail());
        Optional<AppUser> user = appUserRepository.findAppUserByEmail(resetPasswordForm.getEmail());

        if (user.isEmpty()) {
            response.setErrors(List.of(ErrorMessage.builder()
                    .message("Podano błędny adres email")
                    .build()));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setSuccessMessage("Link resetujący hasło został wysłany");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
