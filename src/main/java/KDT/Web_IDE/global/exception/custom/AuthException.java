package KDT.Web_IDE.global.exception.custom;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.GlobalException;

public class AuthException extends GlobalException {
  public AuthException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
