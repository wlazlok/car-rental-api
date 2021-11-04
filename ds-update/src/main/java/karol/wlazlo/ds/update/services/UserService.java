package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.AppUserRepository;
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
        //todo: obsługa błędów, errory po errorCode
        //todo wyslanie mejla z linkiem, zablokowanie konta do czasu aktywowania!

        Response response = new Response();

        Optional<AppUser> user = appUserRepository.findAppUserByEmail(form.getEmail());

        if (user.isPresent()) {
            response.setErrors(List.of(ErrorMessage.builder()
                    .message("Użytkownik o podanym adresie email istnieje")
                    .build()));

            return response;
        }

        AppUser savedUser = appUserRepository.save(mapRegisterFormToAppUser(form));

        emailService.sendActivateEmail(savedUser);

        response.setSuccessMessage("Link aktywujący został wysłany na adres e-mail");

        return response;
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
        //todo: obsługa błędów, przeniesnie logiki do serwisów,errory po errorCode
        //todo wyslanie mejla z linkiem, zablokowanie konta do czasu aktywowania!

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

    private AppUser mapRegisterFormToAppUser(RegisterForm form) {
        //username jako email
        return AppUser.builder()
                .email(form.getEmail())
                .username(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .surname(form.getSurname())
                .street(form.getStreet())
                .houseNumber(form.getHouseNumber())
                .appNumber(form.getAppNumber())
                .postalCode(form.getPostalCode())
                .city(form.getCity())
                .activateAccountUUID(UUID.randomUUID())
                .role(Role.USER)
                .build();
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#?!@$%^&*-";
        return RandomStringUtils.random(15, characters);
    }
}
