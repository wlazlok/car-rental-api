package karol.wlazlo.ds.react.services;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.commons.exceptions.UserContextException;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserContextService {
    //todo: przeniesienie do commons całego security

    @Autowired
    private DSReadClient dsReadClient;

    public AppUser getUserForContext() throws UserContextException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof String) {
            return dsReadClient.getUserByUsername((String) principal);
        } else {
            log.info("user.service.get.user.context.exception");
            throw new UserContextException("user.service.get.user.context.exception");
        }

    }
}
