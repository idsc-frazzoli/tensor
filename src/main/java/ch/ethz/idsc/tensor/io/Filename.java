// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

/* package */ class Filename implements Comparable<Filename> {
  public final File file;
  public final String title;
  public final String extension;

  public Filename(File file) {
    this.file = file;
    String myString = file.getName();
    int index = myString.lastIndexOf('.');
    if (0 <= index) {
      title = myString.substring(0, index);
      extension = myString.substring(index + 1);
    } else {
      title = myString;
      extension = "";
    }
  }

  public boolean hasExtension(String string) {
    return extension.equalsIgnoreCase(string);
  }

  /** @param string if null, produces file with title only, for instance to use as a directory
   * @return */
  public File withExtension(String string) {
    return new File(file.getParentFile(), title + (string == null ? "" : "." + string));
  }

  @Override
  public int compareTo(Filename filename) {
    return file.compareTo(filename.file);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Filename) {
      Filename filename = (Filename) object;
      return file.equals(filename.file);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return file.hashCode();
  }
}
