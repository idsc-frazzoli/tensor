// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.utl.UserHome;

enum GaborMatrixDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor tensor = GaborMatrix.of(255, Tensors.vector(.1, .2), RealScalar.of(.3));
    Export.of(UserHome.Pictures(GaborMatrix.class.getSimpleName() + ".png"), //
        ArrayPlot.of(tensor, ColorDataGradients.CLASSIC));
  }
}
