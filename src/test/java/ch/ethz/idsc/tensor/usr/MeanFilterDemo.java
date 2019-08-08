// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.img.MeanFilter;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.io.Import;

/* package */ enum MeanFilterDemo {
  ;
  public static void main(String[] args) throws Exception {
    String name = "bbc737a0";
    int depth = 10;
    // ---
    File file = HomeDirectory.file(name + ".jpg");
    Tensor image = Import.of(file);
    IntStream.range(0, 3).parallel().forEach(index -> //
    image.set(MeanFilter.of(image.get(Tensor.ALL, Tensor.ALL, index), depth), //
        Tensor.ALL, Tensor.ALL, index));
    // ---
    Export.of( //
        HomeDirectory.file(String.format("%s_mean_%02d.png", name, depth)), //
        Join.of(1, Import.of(file), image));
  }
}
