package karol.wlazlo.model.AdminUserResponse;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserResponse extends AbstractResponse {

    private List<UserResponse> users;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        private Long userId;
        private String username;
        private Boolean enabled;
    }
}
