package karol.wlazlo.commons.repositories;


import karol.wlazlo.model.Security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser getAppUserByUsername(String username);

    Optional<AppUser> findAppUserByEmail(String email);
}
