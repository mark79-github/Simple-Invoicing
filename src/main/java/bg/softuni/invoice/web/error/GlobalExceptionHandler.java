package bg.softuni.invoice.web.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllErrors(Model model,
                                  Exception exception) {

//        if (!model.containsAttribute("message")) {
        model.addAttribute("message", exception.getMessage());
//        }

        return "error";
    }
}
