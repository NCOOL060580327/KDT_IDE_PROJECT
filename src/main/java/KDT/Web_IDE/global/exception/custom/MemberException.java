package KDT.Web_IDE.global.exception.custom;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.GlobalException;

public class MemberException extends GlobalException {
  public MemberException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
