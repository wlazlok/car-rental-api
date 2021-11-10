package karol.wlazlo.model.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;

import java.util.List;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractResponse {

    public List<ErrorMessage> errors;

    public String successMessage;
}
