package karol.wlazlo.ds.react.services;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserContextService {
    //todo obsługa błędów przeniesienie do commons całego security

    @Autowired
    private DSReadClient dsReadClient;

    public AppUser getUserForContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof String) {
            AppUser user =  dsReadClient.getUserByUsername((String) principal);
            return user;
        } else {
            log.info("user.service.get.user.context.exception");
            return null;
        }

    }
}
