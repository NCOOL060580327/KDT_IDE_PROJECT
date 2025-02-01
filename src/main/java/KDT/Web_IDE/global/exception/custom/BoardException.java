package KDT.Web_IDE.global.exception.custom;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.GlobalException;

public class BoardException extends GlobalException {
  public BoardException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
