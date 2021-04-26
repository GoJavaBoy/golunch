package co.uk.golunch.web;

import co.uk.golunch.util.ValidationUtil;
import co.uk.golunch.util.exception.ErrorInfo;
import co.uk.golunch.util.exception.IllegalRequestDataException;
import co.uk.golunch.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);


    private static final Set<String> CONSTRAINS_SET = Set.of(
            "users_unique_email_idx", "meals_unique_user_datetime_idx");

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (String constraint : CONSTRAINS_SET){
                if (lowerCaseMsg.contains(constraint)) {
                    return logAndGetErrorInfo(req, e, false, UNPROCESSABLE_ENTITY);
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, CONFLICT);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> bindValidationError(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e, false, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, INTERNAL_SERVER_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logStackTrace, HttpStatus status) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logStackTrace);
        return ResponseEntity.status(status)
                .body(new ErrorInfo(req.getRequestURL(), e.getMessage(), ValidationUtil.getMessage(rootCause))
                );
    }
}
