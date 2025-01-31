package KDT.Web_IDE.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

  // 400 BAD_REQUEST - 잘못된 요청
  NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자를 포함한 9~16글자여야 합니다."),

  // 401 Unauthorized - 미인증
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 유효기간이 지났습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
  AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

  // 403 Forbidden - 권한 없음
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

  // 404 NotFound - 찾을 수 없음
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),

  // 409
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),
  DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 등록된 닉네임입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
