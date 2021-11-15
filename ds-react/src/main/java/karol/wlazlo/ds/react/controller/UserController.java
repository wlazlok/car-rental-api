package karol.wlazlo.ds.react.controller;

import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.commons.exceptions.UserContextException;
import karol.wlazlo.commons.services.UploadImageService;
import karol.wlazlo.ds.react.services.UserContextService;
import karol.wlazlo.model.AppUserResponse.AppUserBasicResponse;
import karol.wlazlo.model.AppUserResponse.AppUserResponse;
import karol.wlazlo.model.ChangePassword.ChangePasswordRequest;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/react/user")
public class UserController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterForm registerForm) {
        Response response = dsUpdateClient.registerUser(registerForm).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/activate")
    public ResponseEntity<Response> activateUser(@RequestParam("id") String uuid, @RequestParam("usr") Long userId) {
        Response response =  dsUpdateClient.activateUser(uuid, userId).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password/auth")
    public ResponseEntity<Response> resetPasswordAuth(@RequestBody ResetPasswordFormAuth resetPasswordFormAuth) {
        Response response = dsUpdateClient.resetPasswordAuthenticate(resetPasswordFormAuth).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm,
                                                  @RequestParam("id") String uuid,
                                                  @RequestParam("usr") Long userId) {
        Response response = dsUpdateClient.resetPassword(resetPasswordForm, uuid, userId).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<AppUserResponse> updateUser(@RequestBody RegisterForm form) {
        AppUserResponse response = dsUpdateClient.updateUser(form).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request) throws UserContextException {
        Response response = dsUpdateClient.changePassword(request, userContextService.getUserForContext().getUsername()).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/info")
    public ResponseEntity<AppUser> getUserInfo() throws UserContextException {
        return ResponseEntity.status(HttpStatus.OK).body(userContextService.getUserForContext());
    }

    @GetMapping("/info/basic")
    public ResponseEntity<AppUserBasicResponse> getUserInfoBasic() throws UserContextException {
        return ResponseEntity.status(HttpStatus.OK).body(userContextService.getBasic());
    }

    @PostMapping("/avatar")
    public ResponseEntity<AppUserResponse> uploadAvatar(HttpServletRequest servletRequest) throws UserContextException, ServletException, IOException {
        AppUserResponse response = uploadImageService.uploadAvatar(servletRequest, userContextService.getUserForContext().getId());

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
