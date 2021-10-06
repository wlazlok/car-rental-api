package karol.wlazlo.model.Response;

import lombok.Data;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;

import java.util.List;

@Data
public abstract class AbstractResponse {

    public List<ErrorMessage> errors;

    public String successMessage;
}
