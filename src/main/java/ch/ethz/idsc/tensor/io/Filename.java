// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.Serializable;

/* package */ class Filename implements Serializable {
  private final String string;

  public Filename(File file) {
    string = file.getName().toLowerCase();
  }

  public boolean has(Extension extension) {
    return string.endsWith(extension.suffix);
  }
}
