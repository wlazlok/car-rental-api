package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.ds.update.services.UserService;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Response> resetPasswordAuthenticate(@RequestBody ResetPasswordFormAuth resetPasswordFormAuth) {
        Response response = new Response();

        try {
            response = userService.resetPasswordAuthenticate(resetPasswordFormAuth.getEmail());
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm,
                                                  @RequestParam("id") String uuid,
                                                  @RequestParam("usr") Long userId) {
        //todo: ExceptionHandler dla status !OK
        Response response = new Response();

        try {
            response = userService.resetPassword(resetPasswordForm, uuid, userId);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
