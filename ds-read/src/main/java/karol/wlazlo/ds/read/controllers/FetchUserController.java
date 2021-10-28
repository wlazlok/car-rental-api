package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ds/read")
public class FetchUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/user/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {
        return appUserRepository.getAppUserByUsername(username);
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody AppUser user) {
        appUserRepository.save(user);
    }
}
