// code by jph
package ch.ethz.idsc.tensor.io;

/* package */ class Filename {
  private static final char DOT = '.';
  // ---
  /** name of file */
  private final String string;

  /** @param string */
  public Filename(String string) {
    this.string = string;
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
    return Extension.of(string.substring(string.lastIndexOf(DOT) + 1));
  }
}
