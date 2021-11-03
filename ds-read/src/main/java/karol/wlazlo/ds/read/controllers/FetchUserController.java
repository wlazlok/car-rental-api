package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ds/read/user")
public class FetchUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {
        return appUserRepository.getAppUserByUsername(username);
    }

    @PostMapping("/get/email")
    public AppUser getUserByEmail(@RequestBody ResetPasswordForm form) {
        Optional<AppUser> user = appUserRepository.findAppUserByEmail(form.getEmail());

        return user.isEmpty() ? null : user.get();
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody AppUser user) {
        appUserRepository.save(user);
    }
}
