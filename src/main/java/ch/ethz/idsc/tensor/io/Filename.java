// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

/* package */ class Filename {
  private static final char DOT = '.';
  // ---
  /** name of file */
  private final String string;

  public Filename(String string) {
    this.string = string;
  }

  public Filename(File file) {
    this(file.getPath());
  }

  /** @return
   * @throws Exception if this filename does not contain the character `.` */
  public Filename truncate() {
    return new Filename(string.substring(0, string.lastIndexOf(DOT)));
  }

  /** Example:
   * "title.csv.gz" gives {@link Extension#GZ}
   * 
   * @return ultimate extension of file derived from the characters after the last '.'
   * @throws Exception if extension is not listed in {@link Extension} */
  public Extension extension() {
    return Extension.valueOf(string.substring(string.lastIndexOf(DOT) + 1).toUpperCase());
  }
}
