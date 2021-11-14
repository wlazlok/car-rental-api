package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.model.ChangePassword.ChangePasswordRequest;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import karol.wlazlo.model.Security.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmailService emailService;

    //todo: przenieść do konfiguracji
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public Response registerUser(RegisterForm form) {
        Response response = new Response();

        Optional<AppUser> user = appUserRepository.findAppUserByEmail(form.getEmail());

        if (user.isPresent()) {
            throw new CarRentalException("msg.user.email.exist");
        }

        try {
            AppUser savedUser = appUserRepository.save(mapRegisterFormToAppUser(form, null));

            emailService.sendActivateEmail(savedUser);

            response.setSuccessMessage("Link aktywujący został wysłany na adres e-mail");

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("user.service.register.user.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.register.user");
        }
    }

    public Response activateUser(String uuid, Long userId) {
        //todo: logi
        AppUser user = appUserRepository.getById(userId);

        Response response = new Response();

        if (user == null) {
            throw new CarRentalException("msg.err.user.not.found");
        }
        if (user.getActivateAccountUUID() == null) {
            throw new CarRentalException("msg.err.account.activated");
        }
        if (!uuid.equals(user.getActivateAccountUUID().toString())) {
            throw new CarRentalException("msg.err.incorrect.activate.link");
        }

        try {
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setActivateAccountUUID(null);
            user.setAccountNonExpired(true);
            appUserRepository.save(user);

            response.setSuccessMessage("Użytkownik został aktywowany");

            return response;
        } catch (Exception ex) {
            throw new CarRentalException("msg.err.activate.user");
        }
    }

    public Response resetPasswordAuthenticate(String email) {
        Optional<AppUser> foundUser = appUserRepository.findAppUserByEmail(email);
        Response response = new Response();

        if (foundUser.isEmpty()) {
            throw new CarRentalException("msg.err.incorrect.email");
        }

        AppUser user = foundUser.get();

        try {
            String tempPassword = generateRandomPassword();
            user.setResetPasswordUUID(UUID.randomUUID());
            user.setPassword(passwordEncoder.encode(tempPassword));
            user = appUserRepository.save(user);

            emailService.sendResetEmail(user, tempPassword);
            response.setSuccessMessage("Mail resetujący został wysłany");

            return response;
        } catch (Exception ex) {
            throw new CarRentalException("msg.err.reset.password.authenticate");
        }
    }

    public Response resetPassword(ResetPasswordForm resetPasswordForm, String uuid, Long userId) {
        //todo: logi
        Response response = new Response();
        AppUser user = appUserRepository.getById(userId);

        if (user == null) {
            throw new CarRentalException("msg.err.user.not.found");
        }
        if (!user.getEmail().equals(resetPasswordForm.getEmail())) {
            throw new CarRentalException("msg.err.incorrect.user.email");
        }
        if (user.getResetPasswordUUID() == null) {
            throw new CarRentalException("msg.err.reset.password.not.requested");
        }
        if (!uuid.equals(user.getResetPasswordUUID().toString())) {
            throw new CarRentalException("msg.err.incorrect.uuid");
        }
        if (!passwordEncoder.matches(resetPasswordForm.getTempPassword(), user.getPassword())) {
            throw new CarRentalException("msg.err.incorrect.temp.password");
        }

        try {
            user.setResetPasswordUUID(null);
            user.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));
            appUserRepository.save(user);
            response.setSuccessMessage("Hasło zmienione pomyślnie!");
            return response;
        } catch (Exception ex) {
            throw new CarRentalException("msg.err.reset.password.error");
        }
    }

    public AppUser updateUser(RegisterForm form) {

        try {
            AppUser user = appUserRepository.getAppUserByUsername(form.getEmail());

            user = mapRegisterFormToAppUser(form, user);

            return appUserRepository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("msg.err.user.service.update.user {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.update.user");
        }
    }

    public Response changePassword(ChangePasswordRequest request, String username) {
        Response response = new Response();

        if (username == null) {
            throw new CarRentalException("msg.err.user.context.error");
        }

        AppUser user = appUserRepository.getAppUserByUsername(username);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new CarRentalException("msg.err.incorrect.old.password");
        }
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new CarRentalException("msg.password.not.equal");
        }

        try {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            appUserRepository.save(user);
            response.setSuccessMessage("Hasło zmienione pomyślnie");

            return response;
        } catch (Exception ex)  {
            ex.printStackTrace();
            log.warn("user.service.change.password.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.change.password.err");
        }
    }

    public AppUser editUserControl(String type, Long userId) {
        try {

            AppUser user = appUserRepository.getById(userId);

            switch (type) {
                case "enabled": {
                    user.setEnabled(!user.isEnabled());
                    return appUserRepository.save(user);
                }
                case "credentialsNonExpired": {
                    user.setCredentialsNonExpired(!user.isCredentialsNonExpired());
                    return appUserRepository.save(user);
                }
                case "accountNonExpired": {
                    user.setAccountNonExpired(!user.isAccountNonExpired());
                    return appUserRepository.save(user);
                }
                case "ADMIN":
                case "USER": {
                    user.setRole(type.equals("ADMIN") ? Role.ADMIN : Role.USER);
                    return appUserRepository.save(user);
                }
            }

            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("user.service.update.user.control.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.update.user");
        }
    }

    private AppUser mapRegisterFormToAppUser(RegisterForm form, AppUser isNew) {
        //username jako email
        return AppUser.builder()
                .id(isNew == null ? null : isNew.getId())
                .email(isNew == null ? form.getEmail() : isNew.getEmail())
                .username(isNew == null ? form.getEmail() : isNew.getUsername())
                .password(isNew == null ? passwordEncoder.encode(form.getPassword()) : isNew.getPassword())
                .name(form.getName())
                .surname(form.getSurname())
                .street(form.getStreet())
                .houseNumber(form.getHouseNumber())
                .appNumber(form.getAppNumber())
                .postalCode(form.getPostalCode())
                .city(form.getCity())
                .activateAccountUUID(isNew == null ? UUID.randomUUID(): null)
                .role(isNew == null ? Role.USER : isNew.getRole())
                .isAccountNonExpired(isNew != null && isNew.isAccountNonExpired())
                .isAccountNonLocked(isNew != null && isNew.isAccountNonLocked())
                .isCredentialsNonExpired(isNew != null && isNew.isCredentialsNonExpired())
                .isEnabled(isNew != null && isNew.isEnabled())
                .comments(isNew == null ? null : isNew.getComments())
                .build();
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#?!@$%^&*-";
        return RandomStringUtils.random(15, characters);
    }
}
