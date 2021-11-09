package karol.wlazlo.model.ChangePassword;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest extends AbstractResponse {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
