// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.Serializable;

/* package */ class Filename implements Serializable {
  /** name of file in upper case characters */
  private final String string;

  public Filename(File file) {
    string = file.getName().toUpperCase();
  }

  private Filename(String string) {
    this.string = string;
  }

  public Filename truncate() {
    return new Filename(string.substring(0, string.lastIndexOf('.')));
  }

  /** @return ultimate extension of file derived from the characters after the last '.'
   * @throws Exception if extension is not listed in {@link Extension} */
  public Extension extension() {
    return Extension.valueOf(string.substring(string.lastIndexOf('.') + 1));
  }
}
