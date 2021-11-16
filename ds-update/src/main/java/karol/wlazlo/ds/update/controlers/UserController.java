package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.ds.update.services.UserService;
import karol.wlazlo.model.AppUserResponse.AppUserResponse;
import karol.wlazlo.model.ChangePassword.ChangePasswordRequest;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Response.AbstractResponse;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        Response response = new Response();

        try {
            response = userService.registerUser(registerForm);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<Response> activateUser(@RequestParam("id") String uuid, @RequestParam("usr") Long userId) {
        Response response = new Response();

        try {
            response = userService.activateUser(uuid, userId);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm,
                                                  @RequestParam("id") String uuid,
                                                  @RequestParam("usr") Long userId) {
        Response response = new Response();

        try {
            response = userService.resetPassword(resetPasswordForm, uuid, userId);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<AppUserResponse> updateUser(@RequestBody RegisterForm form) {
        AppUserResponse response = new AppUserResponse();

        try {
            response.setUser(userService.updateUser(form));
            response.setSuccessMessage("Pomyślnie zaktualizowano dane");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request, @RequestParam("username") String username) {
        Response response = new Response();

        try {
            response = userService.changePassword(request, username, false);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/admin/change-password")
    public ResponseEntity<Response> changePasswordAdmin(@RequestBody ChangePasswordRequest request, @RequestParam("uId") Long userId) {
        Response response = new Response();

        try {
            response = userService.changePassword(request, appUserRepository.getById(userId).getUsername(), true);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/control")
    public ResponseEntity<?> editControl(@RequestParam("type") String type, @RequestParam("uId") Long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.editUserControl(type, userId));
        } catch (Exception ex) {
            AbstractResponse response = new AbstractResponse();
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/avatar/{userId}")
    public ResponseEntity<AppUserResponse> uploadAvatar(HttpServletRequest request, @PathVariable("userId") Long userId) {
        AppUserResponse response = new AppUserResponse();

        try {
            response = userService.uploadAvatar(request, userId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
