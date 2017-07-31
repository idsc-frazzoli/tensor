// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;
import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.GifSequenceWriter;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.io.Import;

enum GifSequenceWriterDemo {
  ;
  public static void main(String[] args2) {
    File root = UserHome.Pictures("racetrack2");
    String[] files = root.list();
    Arrays.sort(files);
    try {
      GifSequenceWriter writer = GifSequenceWriter.of(new File(root.getParentFile(), "some3b.gif"), 200);
      for (int i = 0; i < files.length; ++i)
        writer.append(ImageFormat.of(Import.of(new File(root, files[i])).get(Tensor.ALL, Tensor.ALL, 1)));
      writer.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    try {
      GifSequenceWriter writer = GifSequenceWriter.of(new File(root.getParentFile(), "some3c.gif"), 200);
      for (int i = 0; i < files.length; ++i)
        writer.append(ImageFormat.of(Import.of(new File(root, files[i]))));
      writer.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
