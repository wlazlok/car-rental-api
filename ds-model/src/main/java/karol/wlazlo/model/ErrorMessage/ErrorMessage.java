package karol.wlazlo.model.ErrorMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String message;
    private String errorCode;
}
