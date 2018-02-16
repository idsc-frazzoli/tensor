// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.img.ColorDataIndexed;
import ch.ethz.idsc.tensor.img.ColorDataLists;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.utl.UserHome;

enum ColorDataListsDemo {
  ;
  public static void main(String[] args) throws IOException {
    List<ColorDataIndexed> list = Arrays.asList(ColorDataLists.values());
    Tensor image = Tensors.empty();
    for (ColorDataIndexed cdi : list) {
      Tensor vector = Tensors.vector(i -> i < cdi.size() ? RealScalar.of(i) : DoubleScalar.INDETERMINATE, 16);
      image.append(vector.map(cdi));
    }
    System.out.println(Dimensions.of(image));
    Tensor large = ImageResize.nearest(image, 12);
    Export.of(UserHome.Pictures("cdi.png"), large);
  }
}
