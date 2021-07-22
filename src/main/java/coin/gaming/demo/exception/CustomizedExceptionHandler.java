package coin.gaming.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends AbstractCustomizedExceptionHandler {

//    public static final String NO_NAME = "No name";
//    public static final String NO_DESCRIPTION = "No description";
//
//    /**
//     * 400 BAD_REQUEST
//     *
//     * @param ex
//     * @param request
//     *
//     * @return
//     */
//    @ExceptionHandler(UnknownFilteringFieldException.class)
//    public final ResponseEntity<Object> handleCommon400BadExpressions(Exception ex, WebRequest request) {
//
//        var status = HttpStatus.BAD_REQUEST;
//        var exceptionResponse = new ExceptionResponse(
//            LocalDateTime.now().toString(),
//            status.value(),
//            status.getReasonPhrase(),
//              getHttpMethodName((ServletWebRequest) request) + " " + description,
//            ex.getMessage());
//        return new ResponseEntity<>(exceptionResponse, status);
//    }
}
