// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.io.HomeDirectory;
import junit.framework.Assert;

public enum TestFile {
  ;
  public static File withExtension(String extension) {
    StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
    String className = stackTraceElement.getClassName();
    int index = className.lastIndexOf('.');
    className = 0 < index //
        ? className.substring(index + 1)
        : className;
    File file = HomeDirectory.file(className + "_" + stackTraceElement.getMethodName() + "." + extension);
    Assert.assertFalse(file.exists());
    return file;
  }
}
