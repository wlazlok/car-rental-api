package karol.wlazlo.commons.utils;

import karol.wlazlo.model.ErrorMessage.ErrorMessage;

import java.util.Locale;
import java.util.ResourceBundle;

public class HandleErrorMessage {

    public static ErrorMessage mapErrorMessage(Exception ex) {
        final Locale locale = new Locale("pl", "PL");
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String errorMessage = "";

        try {
            errorMessage = bundle.getString(ex.getMessage());
        } catch (Exception e) {
            errorMessage = "Niezidentyfikowany błąd";
        }

        return new ErrorMessage().builder()
                .message(errorMessage)
                .errorCode(ex.getMessage())
                .build();
    }

    public static ErrorMessage mapErrorCode(String errorCode) {
        final Locale locale = new Locale("pl", "PL");
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String errorMessage = "";

        try {
            errorMessage = bundle.getString(errorCode);
        } catch (Exception e) {
            errorMessage = "Niezidentyfikowany błąd";
        }

        return new ErrorMessage().builder()
                .message(errorMessage)
                .errorCode(errorCode)
                .build();
    }
}