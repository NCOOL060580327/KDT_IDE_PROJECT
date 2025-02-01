package KDT.Web_IDE.chat.constant;

public enum ChatTestConstant {
  CONTENT("test"),
  ID(1L);

  private final Object value;

  ChatTestConstant(Object value) {
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public <T> T getValue() {
    return (T) value;
  }
}
