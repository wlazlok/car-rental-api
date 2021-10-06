package karol.wlazlo.model.ContactForm;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import karol.wlazlo.model.Response.AbstractResponse;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ContactFormResponse extends AbstractResponse {

    private String message;
}
