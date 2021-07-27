package coin.gaming.demo.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends AbstractCustomizedExceptionHandler {

    /**
     * 401 UNAUTHORIZED
     */
    @ExceptionHandler({FeignException.Unauthorized.class})
    public final ResponseEntity<Object> handleFeignExceptionUnauthorized2(Exception ex, WebRequest request) {

        final var status = HttpStatus.UNAUTHORIZED;
        final var exceptionResponse = new ExceptionResponse(
            LocalDateTime.now().toString(),
            status.value(),
            status.getReasonPhrase(),
            getHttpMethodName((ServletWebRequest) request) + " " + request.getDescription(false),
            ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    /**
     * 400 BAD REQUEST
     */
    @ExceptionHandler({FeignException.BadRequest.class})
    public final ResponseEntity<Object> handleFeignExceptionUnauthorized(Exception ex, WebRequest request) {

        final var status = HttpStatus.BAD_REQUEST;
        final var exceptionResponse = new ExceptionResponse(
            LocalDateTime.now().toString(),
            status.value(),
            status.getReasonPhrase(),
            getHttpMethodName((ServletWebRequest) request) + " " + request.getDescription(false),
            ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
