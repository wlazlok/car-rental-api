package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.ds.read.services.UserService;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@RestController
@RequestMapping("/ds/read/user")
public class FetchUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {
        return appUserRepository.getAppUserByUsername(username);
    }

    @PostMapping("/get/email")
    public AppUser getUserByEmail(@RequestBody ResetPasswordFormAuth form) {
        Optional<AppUser> user = appUserRepository.findAppUserByEmail(form.getEmail());

        return user.isEmpty() ? null : user.get();
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody AppUser user) {
        appUserRepository.save(user);
    }

    @GetMapping("/all")
    public ResponseEntity<AdminUserResponse> getUsers() {
        AdminUserResponse response = new AdminUserResponse();

        try {
            response = userService.getUsers();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<AppUser> getUserById(@RequestParam("uId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserRepository.getById(userId));
    }
}
