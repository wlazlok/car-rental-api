package karol.wlazlo.model.AppUserResponse;

import karol.wlazlo.model.Response.AbstractResponse;
import karol.wlazlo.model.Security.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponse extends AbstractResponse {

    private AppUser user;
}
