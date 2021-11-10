package karol.wlazlo.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import karol.wlazlo.commons.exceptions.UserContextException;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;
import karol.wlazlo.model.Response.AbstractResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerAdvisor {

    @ExceptionHandler(value = {FeignException.class})
    protected ResponseEntity<Object> handleFeignException(FeignException ex) {
        log.warn("exception handler - FeignException - {}", ex.contentUTF8());

        return build(ex, null);
    }

    @ExceptionHandler(value = {UserContextException.class})
    protected ResponseEntity<Object> handleUserContextException(UserContextException ex) {
        log.warn("exception handler - UserContextException - {}", ex.getLocalizedMessage());

        return build(null, "Wystąpił błąd systemu, proszę spróbować ponownie");
    }

    @ExceptionHandler(value = {ServletException.class})
    protected ResponseEntity<Object> handleServletException(ServletException ex) {
        log.warn("exception handler - ServletException - {}", ex.getLocalizedMessage());

        return build(null, "Wystąpił błąd systemu, proszę spróbować ponownie");
    }

    @ExceptionHandler(value = {IOException.class})
    protected ResponseEntity<Object> handleIOException(IOException ex) {
        log.warn("exception handler - IOException - {}", ex.getLocalizedMessage());

        return build(null, "Wystąpił błąd systemu, proszę spróbować ponownie");
    }

    private ResponseEntity<Object> build(FeignException ex, String message) {
        AbstractResponse errorResponse = new AbstractResponse();
        ErrorMessage errorMessage = new ErrorMessage();
        try {
            if (message != null) {
                errorMessage.setMessage(message);
                errorResponse.setErrors(List.of(errorMessage));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            errorResponse = new ObjectMapper().readValue(ex.contentUTF8(), AbstractResponse.class);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            errorMessage.setMessage("Wystąpił błąd systemu, proszę spróbować ponownie");
            errorResponse.setErrors(List.of(errorMessage));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
