package KDT.Web_IDE.global.exception.custom;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.GlobalException;

public class TokenException extends GlobalException {
  public TokenException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
