package karol.wlazlo.ds.react.configuration.security.jwt;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private DSReadClient dsReadClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user;

        try {
            user = dsReadClient.getUserByEmail(ResetPasswordForm.builder().email(email).build());
        } catch (Exception ex) {
            ex.printStackTrace();
            //todo obsluga wyjątków logi
            log.info("load user exception: {}", ex.getMessage());
            throw new UsernameNotFoundException("usre not found");
        }

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        } else {
            return user;
        }
    }
}
