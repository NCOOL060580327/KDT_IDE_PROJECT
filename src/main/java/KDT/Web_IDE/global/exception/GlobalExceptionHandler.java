package KDT.Web_IDE.global.exception;

import KDT.Web_IDE.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GlobalException.class})
    protected BaseResponse handleCustomException(GlobalException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return BaseResponse.onFailure(e.getErrorCode(), null);
    }

}
