package karol.wlazlo.model.ResetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordForm {

    private String email;
    private String tempPassword;
    private String newPassword;
}
