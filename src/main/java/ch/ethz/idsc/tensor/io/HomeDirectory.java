// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/$HomeDirectory.html">$HomeDirectory</a> */
public enum HomeDirectory {
  ;
  private static final File USER_HOME = new File(System.getProperty("user.home"));

  /** @param strings
   * @return /home/user/string[0]/string[1]/... */
  public static File file(String... strings) {
    return concat(USER_HOME, strings);
  }

  /** @param strings
   * @return /home/user/Pictures/string[0]/string[1]/... */
  public static File Pictures(String... strings) {
    return concat(file("Pictures"), strings);
  }

  /** @param strings
   * @return /home/user/Documents/string[0]/string[1]/... */
  public static File Documents(String... strings) {
    return concat(file("Documents"), strings);
  }

  // helper function
  private static File concat(File file, String[] strings) {
    for (int index = 0; index < strings.length; ++index)
      file = new File(file, strings[index]);
    return file;
  }
}
