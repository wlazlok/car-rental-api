package karol.wlazlo.ds.admin.controllers;

import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @PostMapping("/control")
    public AppUser editUserControl(@RequestParam("type") String type, @RequestParam("uId") Long userId) {
        System.out.println(type);
        System.out.println(userId);

        return dsUpdateClient.editControl(type, userId);
    }
}
