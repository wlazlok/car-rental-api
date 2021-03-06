package karol.wlazlo.model.AppUserResponse;

import karol.wlazlo.model.Response.AbstractResponse;
import karol.wlazlo.model.Security.AppUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserResponse extends AbstractResponse {

    private AppUser user;
}
