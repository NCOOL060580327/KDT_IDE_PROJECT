package KDT.Web_IDE.member.constant;

public enum MemberTestConstant {
  EMAIL("test@test.com"),
  PASSWORD("Test1234!@#$"),
  NICKNAME("test"),
  PROFILE("test"),
  TOKEN("test"),
  ID(1L);

  private final Object value;

  MemberTestConstant(Object value) {
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public <T> T getValue() {
    return (T) value;
  }
}
