// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

/* package */ class Filename implements Comparable<Filename> {
  public final File file;
  public final String title;
  public final String extension;

  public Filename(File file) {
    this.file = file;
    String string = file.getName();
    int index = string.lastIndexOf('.');
    if (0 <= index) {
      title = string.substring(0, index);
      extension = string.substring(index + 1);
    } else {
      title = string;
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

  @Override // from Comparable<Filename>
  public int compareTo(Filename filename) {
    return file.compareTo(filename.file);
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof Filename) {
      Filename filename = (Filename) object;
      return file.equals(filename.file);
    }
    return false;
  }

  @Override // from Object
  public int hashCode() {
    return file.hashCode();
  }
}
