package karol.wlazlo.ds.admin.controllers;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.ChangePassword.ChangePasswordRequest;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @Autowired
    private DSReadClient dsReadClient;

    @PostMapping("/control")
    public AppUser editUserControl(@RequestParam("type") String type, @RequestParam("uId") Long userId) {
        return dsUpdateClient.editControl(type, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<AdminUserResponse> getUsers() {
        AdminUserResponse response = dsReadClient.getUsers().getBody();

        if (response.getErrors() != null && !response.getUsers().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<AppUser> getUserById(@RequestParam("uId") Long userId) {
        return dsReadClient.getUserById(userId);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request, @RequestParam("uId") Long userId) {
        Response response = dsUpdateClient.changePasswordAdmin(request, userId).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
