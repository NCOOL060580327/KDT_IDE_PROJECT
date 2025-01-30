package KDT.Web_IDE.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

    // 401 Unauthorized - 미인증
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 유효기간이 지났습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),

    // 403 Forbidden - 권한 없음
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
