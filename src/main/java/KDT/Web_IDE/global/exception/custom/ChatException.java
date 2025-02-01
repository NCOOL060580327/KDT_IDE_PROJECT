package KDT.Web_IDE.global.exception.custom;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.GlobalException;

public class ChatException extends GlobalException {
  public ChatException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
