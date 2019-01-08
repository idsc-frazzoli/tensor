// code by jph
package ch.ethz.idsc.tensor.io;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/$UserName.html">$UserName</a> */
public enum UserName {
  ;
  private static final String USER_NAME = System.getProperty("user.name");

  public static String get() {
    return USER_NAME;
  }

  public static boolean is(String username) {
    return username.equals(get());
  }
}
