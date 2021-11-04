package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.commons.utils.HandleErrorMessage;
import karol.wlazlo.ds.update.services.UserService;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@Slf4j
@RequestMapping("/ds/update/user")
@RestController
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterForm registerForm) {
        //todo: obsługa błędów, przeniesnie logiki do serwisów,errory po errorCode
        //todo wyslanie mejla z linkiem, zablokowanie konta do czasu aktywowania!

        Response response = userService.registerUser(registerForm);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/activate")
    public ResponseEntity<Response> activateUser(@RequestParam("id") String uuid, @RequestParam("usr") Long userId) {
        Response response = new Response();

        try {
             response = userService.activateUser(uuid, userId);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password/auth")
    public ResponseEntity<Response> resetPasswordAuthenticate(@RequestBody ResetPasswordForm resetPasswordForm) {
        Response response = new Response();

        try {
            response = userService.resetPasswordAuthenticate(resetPasswordForm.getEmail());
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
