package karol.wlazlo.ds.read.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private AppUserRepository userRepository;

    public AdminUserResponse getUsers() {

        AdminUserResponse response = new AdminUserResponse();

        try {
            List<AdminUserResponse.UserResponse> users = userRepository.findAll().stream().map(user -> AdminUserResponse.UserResponse.builder()
                            .userId(user.getId())
                            .username(user.getUsername())
                            .enabled(user.isEnabled())
                            .build())
                    .collect(Collectors.toList());

            response.setUsers(users);

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("user.service.read.get.users.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.get.users");
        }
    }

    public AppUser getUserById(Long userId) {
        try {
            return userRepository.getById(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("user.service.get.user.by.id.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }
}
