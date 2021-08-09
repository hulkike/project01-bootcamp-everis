package com.example.mstransaction.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 HTTP [RFC7230] status codes are sometimes not sufficient to convey
 enough information about an error to be helpful.  While humans behind
 Web browsers can be informed about the nature of the problem with an
 HTML [W3C.REC-html5-20141028] response body, non-human consumers of
 so-called "HTTP APIs" are usually not.
 */
@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status) {
        this.status = status;
    }
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
