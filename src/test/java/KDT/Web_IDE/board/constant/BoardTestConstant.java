package KDT.Web_IDE.board.constant;

public enum BoardTestConstant {
  TITLE("test"),
  ID(1L),
  ISLEADER(true);

  private final Object value;

  BoardTestConstant(Object value) {
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public <T> T getValue() {
    return (T) value;
  }
}
