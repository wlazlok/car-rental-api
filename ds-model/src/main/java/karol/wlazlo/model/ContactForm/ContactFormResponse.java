package karol.wlazlo.model.ContactForm;

import lombok.*;
import karol.wlazlo.model.Response.AbstractResponse;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormResponse extends AbstractResponse {

    private String message;
}
