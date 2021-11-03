package karol.wlazlo.model.Register;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterForm {

    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private String surname;
    private String street;
    private String houseNumber;
    private String appNumber;
    private String postalCode;
    private String city;

}
