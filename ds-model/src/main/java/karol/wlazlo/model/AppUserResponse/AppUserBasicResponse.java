package karol.wlazlo.model.AppUserResponse;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserBasicResponse extends AbstractResponse {

    private String username;
    private String name;
    private String avatarUrl;
}
