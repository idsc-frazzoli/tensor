// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/$HomeDirectory.html">$HomeDirectory</a> */
public enum HomeDirectory {
  ;
  private static final File user_home() {
    try {
      return new File(System.getProperty("user.home"));
    } catch (Exception exception) { // security exception, null pointer
      return null;
    }
  }

  private static final File USER_HOME = user_home();

  /** @param strings
   * @return /home/$USERNAME/string[0]/string[1]/... */
  public static File file(String... strings) {
    return concat(USER_HOME, strings);
  }

  /** @param strings
   * @return /home/$USERNAME/Desktop/string[0]/string[1]/... */
  public static File Desktop(String... strings) {
    return subfolder("Desktop", strings);
  }

  /** @param strings
   * @return /home/$USERNAME/Documents/string[0]/string[1]/... */
  public static File Documents(String... strings) {
    return subfolder("Documents", strings);
  }

  /** @param strings
   * @return /home/$USERNAME/Downloads/string[0]/string[1]/... */
  public static File Downloads(String... strings) {
    return subfolder("Downloads", strings);
  }

  /** @param strings
   * @return /home/$USERNAME/Pictures/string[0]/string[1]/... */
  public static File Pictures(String... strings) {
    return subfolder("Pictures", strings);
  }

  // helper function
  private static File subfolder(String folder, String... strings) {
    File file = file(folder);
    file.mkdir();
    return concat(file, strings);
  }

  private static File concat(File file, String[] strings) {
    for (int index = 0; index < strings.length; ++index)
      file = new File(file, strings[index]);
    return file;
  }
}
